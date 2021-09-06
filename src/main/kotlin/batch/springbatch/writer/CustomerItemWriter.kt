package batch.springbatch.writer

import batch.springbatch.model.Customer
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

/**
 * @author Rasung Ki
 */
@Component
class CustomerItemWriter : ItemWriter<Customer> {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun write(items: MutableList<out Customer>) {
       return log.info(items.toString())
    }
}
