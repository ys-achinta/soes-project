package soes.api;

public class StockExchange {
    private StockList stockList;

    public void takeOrders(StockList stockList) {
        this.stockList = stockList;
    }

    public StockList getProcessedStocks() throws Exception {
        processStocks();
        return stockList;
    }

    private void processStocks() throws Exception {
        Stock buyOrder;
        Stock sellOrder;
        for(Stock stock : stockList.getStocks()){
            if(stock.getSide().equals("Buy")){
                buyOrder = stock;
                sellOrder = stockList.getSellStockByCompanyName(stock.getCompany());
            } else{
                buyOrder = stockList.getBuyStockByCompanyName(stock.getCompany());
                sellOrder = stock;
            }

            if(buyOrder != null && sellOrder != null){
                process(buyOrder, sellOrder);
            }
        }
    }

    private void process(Stock buyOrder, Stock sellOrder) throws Exception {
        Integer quantityToBeSold = sellOrder.getQuantityForTransaction();
        Integer quantityToBePurchased = buyOrder.getQuantityForTransaction();

        buyOrder.buy(quantityToBeSold);
        sellOrder.sell(quantityToBePurchased);
    }
}
