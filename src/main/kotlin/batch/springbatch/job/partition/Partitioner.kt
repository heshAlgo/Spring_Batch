package batch.springbatch.job.partition

import org.springframework.batch.core.partition.support.Partitioner
import org.springframework.batch.item.ExecutionContext
import org.springframework.stereotype.Component

@Component
class Partitioner : Partitioner {
    override fun partition(gridSize: Int): MutableMap<String, ExecutionContext> =
        mutableMapOf<String, ExecutionContext>().apply {
            for (index in 1..gridSize) {
                val executionContext = ExecutionContext()
                executionContext.putInt("index", index)
                executionContext.putInt("gridSize", gridSize)
                this["partition${index}"] = executionContext
            }
        }
}
