package soes.api;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class StockTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getQuantityForTransaction(){
        Stock stock = new Stock(1,"Buy","ABC",10);
        Integer quantityForTransaction = stock.getQuantityForTransaction();

        assertEquals("Total stock is the quantityForTransaction", stock.getQuantityForTransaction(), quantityForTransaction);
        stock.setStockRemaining(8);
        Integer newQuantityForTransaction = stock.getQuantityForTransaction();
        assertEquals("Remaining stock is the quantityForTransaction", stock.getStockRemaining(), newQuantityForTransaction);
    }

    @Test
    public void shouldSetStockRemainingForAnOrder(){
        Stock stock = new Stock(1,"Buy","ABC",10);
        Integer amount = 20;
        stock.setStockRemaining(amount);

        assertEquals("Amount more than present quantity, so remaining is zero", new Integer(0) , stock.getStockRemaining());

        Stock stockOne = new Stock(2,"Buy","ABC",20);
        amount = 15;
        stockOne.setStockRemaining(amount);

        assertEquals("Amount less than present quantity, so remaining is not zero",
                new Integer(stockOne.getStockQuantity() - amount) , stockOne.getStockRemaining());
    }

    @Test
    public void shouldSetTheStatusForAnOrderAsOpen(){
        Stock stockOne = new Stock(2,"Buy","ABC",20);
        Integer amount = 15;
        stockOne.setStockRemaining(amount);
        stockOne.setOrderStatus();

        assertEquals("Amount less than present quantity, so status is open",
                Constants.ORDER_STATUS_OPEN , stockOne.getStatus());
    }

    @Test
    public void shouldSetTheStatusForAnOrderAsClosed(){
        Stock stockOne = new Stock(2,"Buy","ABC",20);
        Integer amount = 25;
        stockOne.setStockRemaining(amount);
        stockOne.setOrderStatus();

        assertEquals("Amount more than present quantity, so status is open",
                Constants.ORDER_STATUS_CLOSED , stockOne.getStatus());
    }

    @Test
    public void shouldThrowExceptionIfNotABuyOrderToBuy() throws Exception {
        Stock sellOrder = new Stock(2,"Sell","ABC",20);
        Integer quantityToBeSold = 15;
        expectedException.expect(Exception.class);
        expectedException.expectMessage("Has to be a buy order to buy");

        sellOrder.buy(quantityToBeSold);
    }

    @Test
    public void shouldThrowExceptionIfNotASellOrderToSell() throws Exception {
        Stock buyOrder = new Stock(2,"Buy","ABC",20);
        Integer quantityToBeSold = 15;
        expectedException.expect(Exception.class);
        expectedException.expectMessage("Has to be a sell order to sell");

        buyOrder.sell(quantityToBeSold);
    }

    @Test
    public void shouldSellAStock() throws Exception {
        Stock sellOrder = new Stock(2,"Sell","ABC",20);
        Integer quantityToBePurchased = 15;

        sellOrder.sell(quantityToBePurchased);
        assertEquals("Sell Order is Open", Constants.ORDER_STATUS_OPEN, sellOrder.getStatus());
        assertEquals("Amount Remaining", new Integer(sellOrder.getStockQuantity() - quantityToBePurchased), sellOrder.getStockRemaining());
    }

    @Test
    public void shouldBuyAStock() throws Exception {
        Stock buyOrder = new Stock(2,"Buy","ABC",20);
        Integer quantityToBeSold = 30;

        buyOrder.buy(quantityToBeSold);
        assertEquals("Buy Order is Closed", Constants.ORDER_STATUS_CLOSED, buyOrder.getStatus());
        assertEquals("Amount Remaining", new Integer(0), buyOrder.getStockRemaining());
    }
}
