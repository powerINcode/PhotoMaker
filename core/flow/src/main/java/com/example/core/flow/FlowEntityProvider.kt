package com.example.core.flow

interface FlowEntityProvider {
    fun <T: FlowEntity> getFlow(flow: Class<T>): T
}