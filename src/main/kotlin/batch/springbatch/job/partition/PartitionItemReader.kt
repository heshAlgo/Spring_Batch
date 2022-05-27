package batch.springbatch.job.partition

import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component
import java.util.*

/**
 * @author Rasung Ki
 */
@Component
class PartitionItemReader: ItemReader<Customer> {
    private val queue: Queue<Customer> = LinkedList()

    init {
        queue.add(Customer("1", "test1"))
        queue.add(Customer("2", "test2"))
        queue.add(Customer("3", "test3"))
        queue.add(Customer("4", "test4"))
        queue.add(Customer("5", "test5"))
        queue.add(Customer("6", "test6"))
        queue.add(Customer("7", "test7"))
        queue.add(Customer("8", "test8"))
        queue.add(Customer("9", "test9"))
        queue.add(Customer("1", "test1"))
        queue.add(Customer("2", "test2"))
        queue.add(Customer("3", "test3"))
        queue.add(Customer("4", "test4"))
        queue.add(Customer("5", "test5"))
        queue.add(Customer("6", "test6"))
        queue.add(Customer("7", "test7"))
        queue.add(Customer("8", "test8"))
        queue.add(Customer("9", "test9"))
    }

    override fun read(): Customer? {
        return queue.poll()
    }

}
