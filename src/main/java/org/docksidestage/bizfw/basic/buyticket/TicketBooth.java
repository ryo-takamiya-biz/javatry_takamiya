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
import java.time.LocalTime;

// #1on1: 要件定義のお話、事業会社のジレンマ (2026/10/28)
// $まさしく要件定義苦しんでる、学びはあるので大変だけどまあためになる。
// jflute感想:
// o 業務の全体がわかるようになってくると、実装の立ち位置がわかるようになって楽しくなる。
// o 生活プライベートでも要件定義力が活躍する

// done takamiya javadocにauthorの追加をお願いします by jflute (2026/10/13)
/**
 * @author jflute
 * @author takamiya
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;
    // private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    // private static final int TWO_DAY_PRICE = 13200;
    // private static final int FOUR_DAY_PRICE = 22400;
    // done takamiya コピペミスっぽい、正しい仕様の7400を設定しましょう by jflute (2026/10/13)
    // private static final int NIGHT_ONLY_TWO_DAY_PRICE = 7400;
    public enum TicketType {
        // #1on1: enumは、インスタンスの存在そのものがユニークキー (2026/10/28)
        ONE_DAY(7400, 1, AllowedEntryTimeType.ALL_DAY),
        TWO_DAY(13200, 2, AllowedEntryTimeType.ALL_DAY),
        FOUR_DAY(22400, 4, AllowedEntryTimeType.ALL_DAY),
        NIGHT_ONLY_TWO_DAY(7400, 2, AllowedEntryTimeType.NIGHT);

        private final int price;
        private final int maxEntryCount;
        private final AllowedEntryTimeType allowedEntryTimeType;

        TicketType(int price, int maxEntryCount, AllowedEntryTimeType allowedEntryTimeType) {
            this.price = price;
            this.maxEntryCount = maxEntryCount;
            this.allowedEntryTimeType = allowedEntryTimeType;
        }

        public int getPrice() { return price; }

        public int getMaxEntryCount() { return maxEntryCount; }

        public AllowedEntryTimeType getAllowedEntryTimeType() { return allowedEntryTimeType; }
    }

    public enum AllowedEntryTimeType {
        // #1on1: minutesのこと考えたら、最初からLocalTimeでGood (2026/10/28)
        ALL_DAY(LocalTime.of(9, 0), LocalTime.of(21, 0)),
        MORNING(LocalTime.of(9, 0), LocalTime.of(12, 0)),
        AFTERNOON(LocalTime.of(12, 0), LocalTime.of(17, 0)),
        NIGHT(LocalTime.of(17, 0), LocalTime.of(21, 0));

        private final LocalTime startTime;
        private final LocalTime endTime;

        AllowedEntryTimeType (LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public LocalTime getStartTime() { return startTime; }

        public LocalTime getEndTime() { return endTime; }

        public boolean isAllowedEntry(LocalTime currentTime) {
            return !currentTime.isAfter(startTime) && currentTime.isAfter(endTime);
        }
    }

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // #1on1: いいね、在庫を分けるスタイルGood (2026/09/25)
    // $迷った。実際売られる場合を想像した時に、在庫一緒は現実的じゃないな。
    // 色々なパターンを想像してみて、在庫分離の方が自然かも。
    // done takamiya NightOnlyTwoDayQuantity 変数だけ先頭が大文字 by jflute (2026/10/13)
    private int oneDayQuantity = MAX_QUANTITY;
    private int twoDayQuantity = MAX_QUANTITY;
    private int fourDayQuantity = MAX_QUANTITY;
    private int nightOnlyTwoDayQuantity = MAX_QUANTITY;
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
    // done takamiya $returnになってる。@returnが正しい。 by jflute (2026/10/13)
    /**
     * Buy one-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     * @return チケットクラス
     */
    public TicketBuyResult buyOneDayPassport(Integer handedMoney) {
        // done takamiya "allDay", "night" はハードコードの文字列ではなく... by jflute (2026/10/13)
        // せめて、static final な定数にしましょう。スペルミスによる間違いも防ぎたいし、
        // リファクタリングでallDay, nightの中身の文字列を直す時も、何ヶ所も修正しなくても済むように。
        oneDayQuantity = doBuyPassport(handedMoney, oneDayQuantity, TicketType.ONE_DAY);
        return new TicketBuyResult(new Ticket(TicketType.ONE_DAY), handedMoney);
    }

    // #1on1: twoDay以降もJavaDocが入っていてGood (2026/10/13)
    // 特にpublicメソッドは、呼び出しインターフェースなので、JavaDocで案内があると呼び出し側は嬉しい。
    // done takamiya JavaDoc, @return も追加をお願いします by jflute (2026/09/25)
    /**
    * 2Dayパスポートを買う、パークゲスト用のメソッド。
    * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
    * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
    * @throws TicketShortMoneyException 買うのに金額が足りなかったら
    * @return チケット購入結果
    */
    public TicketBuyResult buyTwoDayPassport(Integer handedMoney) {
        // done takamiya TicketBuyResultの行も、再利用をどうにかしたいところ by jflute (2026/10/13)
        twoDayQuantity = doBuyPassport(handedMoney, twoDayQuantity, TicketType.TWO_DAY);
        return new TicketBuyResult(new Ticket(TicketType.TWO_DAY), handedMoney);
    }

    /**
     * 4Dayパスポートを買う、パークゲスト用のメソッド。
     * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
     * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
     * @throws TicketShortMoneyException 買うのに金額が足りなかったら
     * @return チケット購入結果
     */
    public TicketBuyResult buyFourDayPassport(Integer handedMoney) {
        fourDayQuantity = doBuyPassport(handedMoney, fourDayQuantity, TicketType.FOUR_DAY);
        return new TicketBuyResult(new Ticket(TicketType.FOUR_DAY), handedMoney);
    }
    // #1on1: $関数で切り出す時の基準は？ (見方を変えれば1行だけ) (2026/10/28)
    // 基本: コードの見た目が同じだからというよりかは、意味が同じだから切り出す
    //      (意味が同じであれば、一つのケースだけちょっと変わるみたいな確率が低い)
    //      (追記: コードの見た目が同じで意味が違うケース、業務仕様の整理整頓がうまくいってないことが要因の一つでも)
    // 応用: 行数よりも意味の存在感で切り出し判断
    //      (オブジェクト指向的な面でもオーバーライドしたくなりやすいかどうか)
    //      (特に重要オブジェクトをnewしているところ、newの先のコードの行数があると考えても良い)
    //
    // $現実の仕様と当てはめて実装の粒度も表現できたら...
    // → yes, 最高。それが自然と拡張のしやすいコードにもなると思う
    //   一方で、現実の業務仕様がしっかり整理整頓されてることが前提
    //
    // LastaFluteのbeforeFire()のaskBeginTime()を例にコードリーディング。
    //
    // TODO takamiya "return new TicketBuyResult" のところ、メソッド化してみてください by jflute (2026/10/28)

    /**
     * 夜用2Dayパスポートを買う、パークゲスト用のメソッド。
     * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
     * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
     * @throws TicketShortMoneyException 買うのに金額が足りなかったら
     * @return チケット購入結果
     */
    public TicketBuyResult buyNightOnlyTwoDayPassport(Integer handedMoney) {
        nightOnlyTwoDayQuantity = doBuyPassport(handedMoney, nightOnlyTwoDayQuantity, TicketType.NIGHT_ONLY_TWO_DAY);
        return new TicketBuyResult(new Ticket(TicketType.NIGHT_ONLY_TWO_DAY), handedMoney);
    }

    // done takamiya publicのbuyとprivateのbuyを区別するテクニック by jflute (2026/10/13)
    // buyがメソッド一覧で混ざるのと、会話上ちょっと紛れやすいと言うのもあって、メソッド名を工夫して区別したりする。
    // e.g. doBuyPassport(), internalBuyPassport()
    private int doBuyPassport(Integer handedMoney, int quantity, TicketType ticketType) {
        int price = ticketType.getPrice();

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

    // done takamiya コピペミス by jflute (2026/10/13)
    public int getFourDayQuantity() {
        return fourDayQuantity;
    }

    // done takamiya nightOnlyのquantityのgetterもあった方がバランス良いかなと by jflute (2026/10/13)
    public int getNightOnlyTwoDayQuantity() {
        return nightOnlyTwoDayQuantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
