
@file:JvmName("ViewBindingUtil")
package com.panda.commonlibrary.viewbind

/**
 *   <pre>
 *   author: pandaWang
 *   time: 2021/1/18 11:01
 *   version: v1.0
 *   des:
 *   <pre>
 */

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


inline fun <reified VB : ViewBinding> Activity.binding() = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}

inline fun <reified VB : ViewBinding> Dialog.binding() = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}

inline fun <reified VB : ViewBinding> Fragment.binding() =
    FragmentBindingDelegate(VB::class.java)

inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB

inline fun <reified VB : ViewBinding> inflateBinding(parent: ViewGroup) =
    inflateBinding<VB>(LayoutInflater.from(parent.context), parent, false)

inline fun <reified VB : ViewBinding> inflateBinding(
    layoutInflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean
) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        .invoke(null, layoutInflater, parent, attachToParent) as VB

class FragmentBindingDelegate<VB : ViewBinding>(
    private val clazz: Class<VB>
) : ReadOnlyProperty<Fragment, VB> {

    private var isInitialized = false
    private var _binding: VB? = null
    private val binding: VB get() = _binding!!

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (!isInitialized) {
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroyView() {
                    _binding = null
                }
            })
            _binding = clazz.getMethod("bind", View::class.java)
                .invoke(null, thisRef.requireView()) as VB
            isInitialized = true
        }
        return binding
    }
}

inline fun <reified VB : ViewBinding> BindingViewHolder(parent: ViewGroup) =
    BindingViewHolder(inflateBinding<VB>(parent))

class BindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root) {

    constructor(block: (LayoutInflater, ViewGroup, Boolean) -> VB, parent: ViewGroup) :
            this(block(LayoutInflater.from(parent.context), parent, false))
}

@Suppress("UNCHECKED_CAST")
@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(layoutInflater: LayoutInflater): VB =
    withGenericBindingClass(this) {
        it.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB
    }

@Suppress("UNCHECKED_CAST")
@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(view: View): VB =
    withGenericBindingClass(this) {
        it.getMethod("bind", LayoutInflater::class.java).invoke(null, view) as VB
    }

@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(parent: ViewGroup): VB =
    withGenericBindingClass(this) { inflateBinding(it, LayoutInflater.from(parent.context), parent, false) }

@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(
    layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean
): VB =
    withGenericBindingClass(this) { inflateBinding(it, layoutInflater, parent, attachToParent) }

@Suppress("UNCHECKED_CAST")
private fun <VB : ViewBinding> inflateBinding(
    clazz: Class<VB>, layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean
) =
    clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        .invoke(null, layoutInflater, parent, attachToParent) as VB

private fun <VB : ViewBinding> withGenericBindingClass(any: Any, block: (Class<VB>) -> VB): VB {
    var index = 0
    while (true) {
        try {
            return block.invoke(any.findGenericBindingClass(index))
        } catch (e: NoSuchMethodException) {
            index++
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun <VB : ViewBinding> Any.findGenericBindingClass(index: Int): Class<VB> {
    val type = javaClass.genericSuperclass
    if (type is ParameterizedType && index < type.actualTypeArguments.size) {
        return type.actualTypeArguments[index] as Class<VB>
    }
    throw IllegalArgumentException("There is no generic of ViewBinding.")
}