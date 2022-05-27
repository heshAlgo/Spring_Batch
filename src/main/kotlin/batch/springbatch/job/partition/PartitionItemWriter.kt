package batch.springbatch.job.partition

import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

/**
 * @author Rasung Ki
 */
@Component
class PartitionItemWriter: ItemWriter<Customer> {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun write(items: MutableList<out Customer>) {
        log.info("items:$items")
    }
}
