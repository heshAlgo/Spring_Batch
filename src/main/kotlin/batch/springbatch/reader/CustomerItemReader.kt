package batch.springbatch.reader

import batch.springbatch.model.Customer
import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component

/**
 * @author Rasung Ki
 */
@Component
class CustomerItemReader : ItemReader<Customer> {
    private val itemList = mutableListOf<Customer>().also {
        for (index in 1..10000) {
            it.add(Customer(index, "#${index} 홍길동"))
            println("test testetstste ")
        }
    }

    override fun read(): Customer? {
        return if (itemList.isNotEmpty()) {
            itemList.removeAt(0)
        } else {
            null
        }
    }
}
