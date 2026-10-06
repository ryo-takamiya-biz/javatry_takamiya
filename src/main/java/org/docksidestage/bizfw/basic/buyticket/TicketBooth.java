/*
 * Copyright 2019-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author jflute
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200;
    private static final int FOUR_DAY_PRICE = 22400;
    private static final int NIGHT_ONLY_TWO_DAY_PRICE = 13200;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // #1on1: いいね、在庫を分けるスタイルGood (2026/09/25)
    // $迷った。実際売られる場合を想像した時に、在庫一緒は現実的じゃないな。
    // 色々なパターンを想像してみて、在庫分離の方が自然かも。
    private int oneDayQuantity = MAX_QUANTITY;
    private int twoDayQuantity = MAX_QUANTITY;
    private int fourDayQuantity = MAX_QUANTITY;
    private int NightOnlyTwoDayQuantity = MAX_QUANTITY;
    private Integer salesProceeds; // null allowed: until first purchase

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    // you can rewrite comments for your own language by jflute
    // e.g. Japanese
    // /**
    // * 1Dayパスポートを買う、パークゲスト用のメソッド。
    // * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
    // * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
    // * @throws TicketShortMoneyException 買うのに金額が足りなかったら
    // */
    /**
     * Buy one-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     * $return チケットクラス
     */
    public Ticket buyOneDayPassport(Integer handedMoney) {
        oneDayQuantity = buyPassport(handedMoney, oneDayQuantity, ONE_DAY_PRICE);
        return new Ticket(ONE_DAY_PRICE, 1, "allDay");
    }

    // done takamiya JavaDoc, @return も追加をお願いします by jflute (2026/09/25)
    /**
    * 2Dayパスポートを買う、パークゲスト用のメソッド。
    * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
    * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
    * @throws TicketShortMoneyException 買うのに金額が足りなかったら
    * @return チケット購入結果
    */
    public TicketBuyResult buyTwoDayPassport(Integer handedMoney) {
        twoDayQuantity = buyPassport(handedMoney, twoDayQuantity, TWO_DAY_PRICE);
        return new TicketBuyResult(new Ticket(TWO_DAY_PRICE, 2, "allDay"), handedMoney - TWO_DAY_PRICE);
    }

    /**
     * 4Dayパスポートを買う、パークゲスト用のメソッド。
     * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
     * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
     * @throws TicketShortMoneyException 買うのに金額が足りなかったら
     * @return チケット購入結果
     */
    public TicketBuyResult buyFourDayPassport(Integer handedMoney) {
        fourDayQuantity = buyPassport(handedMoney, fourDayQuantity, FOUR_DAY_PRICE);
        return new TicketBuyResult(new Ticket(FOUR_DAY_PRICE, 4, "allDay"), handedMoney - FOUR_DAY_PRICE);
    }

    /**
     * 夜用2Dayパスポートを買う、パークゲスト用のメソッド。
     * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
     * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
     * @throws TicketShortMoneyException 買うのに金額が足りなかったら
     * @return チケット購入結果
     */
    public TicketBuyResult buyNightOnlyTwoDayPassport(Integer handedMoney) {
        NightOnlyTwoDayQuantity = buyPassport(handedMoney, NightOnlyTwoDayQuantity, NIGHT_ONLY_TWO_DAY_PRICE);
        return new TicketBuyResult(new Ticket(NIGHT_ONLY_TWO_DAY_PRICE, 2, "night"), handedMoney - NIGHT_ONLY_TWO_DAY_PRICE);
    }

    private int buyPassport(Integer handedMoney, int quantity, int price) {
        if (quantity <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney < price) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
        --quantity;
        if (salesProceeds != null) { // second or more purchase
            salesProceeds = salesProceeds + price;
        } else { // first purchase
            salesProceeds = price;
        }
        return quantity;
    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    // #1on1: 迷いポイント、getQuantity() or getOneDayQuantity()？ (2026/09/25)
    // $単純にどこまでいじっていいかを迷った
    // 在庫分離の概念を取り入れてるからには、メソッドの形も合わせても良いかなと。
    // ただ、既存コードなので、呼び出し側を確認して動作的にOKかどうか見てから。
    // 全部そのままで大丈夫なパターンになっている（＞＜。全部oneDayを想定している。
    // トータルニュアンスでgetしているところがあったとしたら、そのままではいけない。
    // done takamiya 決めの問題ですが、OneDay/TwoDayと対比させるようにしましょう。 by jflute (2026/09/25)
    // (getQuantity()のままだと、ちょっとトータルを出すのかな？って思ってしまう人もいるかも)
    public int getOneDayQuantity() {
        return oneDayQuantity;
    }

    public int getTwoDayQuantity() {
        return twoDayQuantity;
    }

    public int getFourDayQuantity() {
        return twoDayQuantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
