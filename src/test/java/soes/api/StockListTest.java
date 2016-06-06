package soes.api;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class StockListTest {

    StockList stockList;
    @Before
    public void testSetUp(){
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

        stockList = new StockList(orders);
    }

    @Test
    public void shouldGetFirstSellStockByCompanyName(){
        String companyName = "XYZ";
        Stock saleStock = stockList.getSellStockByCompanyName(companyName);

        assertEquals("First sale stock is ",stockList.getStocks().get(0).getStockId(),saleStock.getStockId() );
        assertNotEquals("First sale stock is not ",stockList.getStocks().get(3).getStockId(),saleStock.getStockId() );
    }


    @Test
    public void shouldGetFirstPurchaseStockByCompanyName(){
        String companyName = "ABC";
        Stock saleStock = stockList.getBuyStockByCompanyName(companyName);

        assertEquals("First purchase stock is ",stockList.getStocks().get(4).getStockId(),saleStock.getStockId() );
        assertNotEquals("First purchase stock is not ",stockList.getStocks().get(5).getStockId(),saleStock.getStockId() );
    }

    @Test
    public void shouldReturnNullIThereIsNoMatchingStockByCompanyName(){
        String companyName = "ABCD";
        Stock saleStock = stockList.getBuyStockByCompanyName(companyName);
        assertNull("No matching stock found", saleStock);
    }

    @Test
    public void shouldNotReturnAClosedStockByCompanyName(){
        stockList.getStocks().get(0).setStockRemaining(10);
        stockList.getStocks().get(0).setOrderStatus();

        String companyName = "XYZ";
        Stock saleStock = stockList.getSellStockByCompanyName(companyName);

        assertNotEquals("Sale Stock is closed, First sale stock is not",
                stockList.getStocks().get(0).getStockId(),saleStock.getStockId() );
        assertEquals("First sale stock is ",stockList.getStocks().get(3).getStockId(),saleStock.getStockId() );
    }
}
