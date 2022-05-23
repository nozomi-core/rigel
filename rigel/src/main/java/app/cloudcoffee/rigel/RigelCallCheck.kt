package app.cloudcoffee.rigel

import java.lang.RuntimeException

interface RigelCallGroup {
    fun createCaller(rigelAssert: RigelAssert): RigelCallCheck

    fun allCalled()
    fun allNotCalled()

    fun allCalledTimes(calledTimes: Int)
    fun allNotCalledTimes(calledTimes: Int)
}

class ActualRigelCallGroup: RigelCallGroup {
    private val callerList = ArrayList<RigelCallCheck>()

    override fun createCaller(rigelAssert: RigelAssert): RigelCallCheck {
        return ActualRigelCaller(rigelAssert).also {
            callerList.add(it)
        }
    }

    override fun allCalled() {
        callerList.forEach {
            it.isCalled()
        }
    }

    override fun allNotCalled() {
        callerList.forEach {
            it.isNotCalled()
        }
    }

    override fun allCalledTimes(calledTimes: Int) {
        callerList.forEach {
            it.isCalledTimes(calledTimes)
        }
    }

    override fun allNotCalledTimes(calledTimes: Int) {
        callerList.forEach {
            it.isNotCalledTimes(calledTimes)
        }
    }
}

interface RigelCallCheck {
    fun call(caller: Any)
    fun freeze(caller: Any)
    //fun open(caller: Any)
    //fun close(caller: Any)

    fun isCalled()
    fun isNotCalled()

    fun isCalledTimes(timesCalled: Int)
    fun isNotCalledTimes(timesCalled: Int)

    fun hasCallingClass(callerClass: Any)

    fun getCallCount(): Int
}

enum class CallState {
    OPEN,
    CLOSED,
    FROZEN
}

class ActualRigelCaller(val inspect: RigelAssert): RigelCallCheck {
    private var callState: CallState = CallState.OPEN
    private var frozenCallerClass : Class<Any>? = null

    private var countCallTimes = 0

    private val allClassCallers = HashSet<Class<Any>>()

    private fun checkCallState(caller: Any) {
        if(callState == CallState.FROZEN) {
            throw RuntimeException("Caller is currently frozen, was called by ${caller.javaClass.canonicalName}")
        }
        allClassCallers.add(caller.javaClass)
    }

    private fun checkChangeState(caller: Any, newState: CallState) {
        if(callState == CallState.FROZEN) {
            throw RuntimeException("Caller is currently frozen, state changed by ${caller.javaClass.canonicalName}")
        }
        callState = newState
    }

    override fun call(caller: Any) {
        checkCallState(caller)
        countCallTimes++
    }

    override fun freeze(caller: Any) {
        callState = CallState.FROZEN
        frozenCallerClass = caller.javaClass
    }

    //TODO: future dev make open / close
    private fun open(caller: Any) {
        checkChangeState(caller, CallState.OPEN)
    }

    //TODO: future dev make open / close
    private fun close(caller: Any) {
        checkChangeState(caller, CallState.CLOSED)
    }

    override fun isCalled() {
        inspect.assertTrue(countCallTimes > 0)
    }

    override fun isNotCalled() {
        inspect.assertTrue(countCallTimes == 0)
    }

    override fun isCalledTimes(timesCalled: Int) {
        inspect.assertTrue(countCallTimes == timesCalled)
    }

    override fun isNotCalledTimes(timesCalled: Int) {
        inspect.assertTrue(countCallTimes != timesCalled)
    }

    override fun hasCallingClass(callerClass: Any) {
        inspect.assertTrue(allClassCallers.contains(callerClass.javaClass))
    }

    override fun getCallCount(): Int {
        return countCallTimes
    }
}