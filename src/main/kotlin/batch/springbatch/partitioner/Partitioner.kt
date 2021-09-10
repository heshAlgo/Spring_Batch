package batch.springbatch.partitioner

import org.slf4j.LoggerFactory
import org.springframework.batch.core.partition.support.Partitioner
import org.springframework.batch.item.ExecutionContext
import org.springframework.stereotype.Component

/**
 * @author Rasung Ki
 */
@Component
class Partitioner : Partitioner {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun partition(gridSize: Int): MutableMap<String, ExecutionContext> {
        val map = mutableMapOf<String, ExecutionContext>()
        log.info("Grid_Size:${gridSize}")
        for (index in 1..4) {
            val ctx = ExecutionContext()
            ctx.putInt("gridSize", gridSize)
            ctx.putInt("index", index)
            map["partition${index}"] = ctx
        }

        return map
    }
}
