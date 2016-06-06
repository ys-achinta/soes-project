package soes.api;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class StockExchangeTest {

    StockExchange stockExchange = new StockExchange();

    @Test
    public void shouldProcessGivenOrdersList1_AndGetTheStatus_AndQuantityRemaining() throws Exception {

        List<Stock> orders = new ArrayList<>();
        Stock order1 = new Stock(1,"Buy","ABC", 10);
        Stock order2 = new Stock(2,"Sell","XYZ", 15);
        Stock order3 = new Stock(3,"Sell","ABC", 13);
        Stock order4 = new Stock(4,"Buy","XYZ", 10);
        Stock order5 = new Stock(5,"Buy","XYZ", 8);

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
        orders.add(order5);

        StockList stockList = new StockList(orders);

        stockExchange.takeOrders(stockList);
        stockExchange.getProcessedStocks();

        List<Stock> processedOrders = stockList.getStocks();

        assertEquals("Buy order for ABC status", processedOrders.get(0).getStatus(), "Closed" );
        assertEquals("Buy order for ABC QuantityRemaining", processedOrders.get(0).getStockRemaining(), new Integer(0) );

        assertEquals("Sell order for XYZ status", processedOrders.get(1).getStatus(), "Closed" );
        assertEquals("Sell order for XYZ quantityRemaining", processedOrders.get(1).getStockRemaining(), new Integer(0));

        assertEquals("Sell order for ABC status", processedOrders.get(2).getStatus(), "Open" );
        assertEquals("Sell order for ABC quantityRemaining", processedOrders.get(2).getStockRemaining(), new Integer(3) );

        assertEquals("Buy order for XYZ status", processedOrders.get(3).getStatus(), "Closed" );
        assertEquals("Buy order for XYZ quantityRemaining", processedOrders.get(3).getStockRemaining(), new Integer(0) );

        assertEquals("Buy order for XYZ status", processedOrders.get(4).getStatus(), "Open" );
        assertEquals("Buy order for XYZ quantityRemaining", processedOrders.get(4).getStockRemaining(), new Integer(3) );
    }


    @Test
    public void shouldProcessGivenOrderList2_AndGetTheCorrectStatus_AndQuantityRemaining() throws Exception {

        List<Stock> orders = new ArrayList<>();
        Stock order1 = new Stock(1,"Sell","XYZ", 10);
        Stock order2 = new Stock(2,"Sell","ABC", 20);
        Stock order3 = new Stock(3,"Buy","XYZ", 20);
        Stock order4 = new Stock(4,"Sell","XYZ", 20);
        Stock order5 = new Stock(5,"Buy","ABC", 15);
        Stock order6 = new Stock(6,"Buy","ABC", 5);

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
        orders.add(order5);
        orders.add(order6);

        StockList stockList = new StockList(orders);

        stockExchange.takeOrders(stockList);
        stockExchange.getProcessedStocks();

        List<Stock> processedOrders = stockList.getStocks();

        assertEquals("Sell order for XYZ status", processedOrders.get(0).getStatus(), "Closed" );
        assertEquals("Sell order for XYZ QuantityRemaining", processedOrders.get(0).getStockRemaining(), new Integer(0) );

        assertEquals("Sell order for ABC status", processedOrders.get(1).getStatus(), "Closed" );
        assertEquals("Sell order for ABC quantityRemaining", processedOrders.get(1).getStockRemaining(), new Integer(0));

        assertEquals("Buy order for XYZ status", processedOrders.get(2).getStatus(), "Closed" );
        assertEquals("Buy order for XYZ quantityRemaining", processedOrders.get(2).getStockRemaining(), new Integer(0) );

        assertEquals("Sell order for XYZ status", processedOrders.get(3).getStatus(), "Open" );
        assertEquals("Sell order for XYZ quantityRemaining", processedOrders.get(3).getStockRemaining(), new Integer(10) );

        assertEquals("Buy order for ABC status", processedOrders.get(4).getStatus(), "Closed" );
        assertEquals("Buy order for ABC quantityRemaining", processedOrders.get(4).getStockRemaining(), new Integer(0) );

        assertEquals("Buy order for ABC status", processedOrders.get(5).getStatus(), "Closed" );
        assertEquals("Buy order for ABC quantityRemaining", processedOrders.get(5).getStockRemaining(), new Integer(0) );
    }
}
