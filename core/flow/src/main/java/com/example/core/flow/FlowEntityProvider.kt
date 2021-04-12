package com.example.core.flow

/**
 * Provides [FlowEntity]
 */
interface FlowEntityProvider {
    /**
     * Provide [FlowEntity] by the class
     * @param flow describe class of the requested [FlowEntity]
     */
    fun <T: FlowEntity> getFlow(flow: Class<T>): T
}