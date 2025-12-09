package stream.api;

import common.test.tool.annotation.Difficult;
import common.test.tool.dataset.ClassicOnlineStore;
import common.test.tool.entity.Customer;
import common.test.tool.entity.Item;
import common.test.tool.entity.Shop;

import java.util.Comparator;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class Exercise8Test extends ClassicOnlineStore {

    @Difficult
    @Test
    public void itemsNotOnSale() {
        Stream<Customer> customerStream = this.mall.getCustomerList().stream();
        Stream<Shop> shopStream = this.mall.getShopList().stream();

        /**
         * Create a set of item names that are in {@link Customer.wantToBuy}
         * but not on sale in any shop.
         */

        // All item names that customers want
        List<String> itemListWanted =
                customerStream
                        .flatMap(c -> c.getWantToBuy().stream())
                        .map(Item::getName)
                        .collect(Collectors.toList());

        // All item names that appear in shops
        List<String> itemListOnSale =
                this.mall.getShopList().stream()
                        .flatMap(shop -> shop.getItemList().stream())
                        .map(Item::getName)
                        .distinct()
                        .collect(Collectors.toList());

        // Wanted but not on sale
        Set<String> itemSetNotOnSale =
                itemListWanted.stream()
                        .filter(name -> !itemListOnSale.contains(name))
                        .collect(Collectors.toSet());

        assertThat(itemSetNotOnSale, hasSize(3));
        assertThat(itemSetNotOnSale, hasItems("bag", "pants", "coat"));
    }

    @Difficult
    @Test
    public void havingEnoughMoney() {
        Stream<Customer> customerStream = this.mall.getCustomerList().stream();
        Stream<Shop> shopStream = this.mall.getShopList().stream();

        /**
         * Create a customer's name list including those who have enough money to buy
         * all items they want which ARE on sale.
         * Items not on sale count as cost 0.
         * If multiple shops sell same item, choose the cheapest.
         */

        // Build a map: itemName -> cheapest price
        var cheapestPriceMap =
                this.mall.getShopList().stream()
                        .flatMap(shop -> shop.getItemList().stream())
                        .collect(Collectors.groupingBy(
                                Item::getName,
                                Collectors.collectingAndThen(
                                        Collectors.minBy(Comparator.comparingInt(Item::getPrice)),
                                        opt -> opt.get().getPrice()
                                )
                        ));

        // Filter customers with enough money
        List<String> customerNameList =
                this.mall.getCustomerList().stream()
                        .filter(customer -> {
                            // Total cost = sum of cheapest available prices for items that *are* on sale
                            int totalCost =
                                    customer.getWantToBuy().stream()
                                            .map(Item::getName)
                                            .mapToInt(name -> cheapestPriceMap.getOrDefault(name, 0))
                                            .sum();

                            return customer.getBudget() >= totalCost;
                        })
                        .map(Customer::getName)
                        .collect(Collectors.toList());

        assertThat(customerNameList, hasSize(7));
        assertThat(customerNameList, hasItems("Joe", "Patrick", "Chris", "Kathy", "Alice", "Andrew", "Amy"));
    }
}
