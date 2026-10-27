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

/**
 * @author jflute
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // #1on1: "入場可能な回数", "入場した回数" の横のコメントGood (2026/10/13)
    // "入場可能な回数" という概念、現在の入場可能な回数なのか？そのチケット種別で入れるMAX回数なのか？
    // done takamiya なので、"入場可能な回数" は、もうちょいしっかり固定値な日数であることがわかるような概念名を付けよう by jflute (2026/10/13)
    private final int displayPrice; // written on ticket, park guest can watch this
    private final int maxEntryCount; // 入場可能な最大回数
    private int entryCount = 0; // 入場した回数
    private final TicketBooth.AllowedEntryTimeType allowedEntryTimeType;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Ticket(TicketBooth.TicketType ticketType) {
        this.displayPrice = ticketType.getPrice();
        this.maxEntryCount = ticketType.getMaxEntryCount();
        this.allowedEntryTimeType = ticketType.getAllowedEntryTimeType();
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    // done takamiya 引数名、「入園の時間帯」つまり実質「今の時間帯」なので、何かしらそのニュアンスの名前が欲しい by jflute (2026/10/13)
    // TODO takamiya 修行++: 呼び出し側が時間帯を入れると呼び出し側で細工ができて予期せぬ挙動も許してしまう。 by jflute (2026/10/13)
    // 時間をdoInPark()側で取得できるようにしたが、関数呼び出し側から時間を固定する方法はわからなかった
    // なので、「入園の時間帯」は doInPark() 内で導出して、判定するようにしたいところ。
    // ただ、テストのときは「入園の時間帯」を細工して動作確認したいので、そことトレードオフになっちゃう。
    // そのトレードオフをどうにかして回避したいところだが、難易度が高いので修行++扱い。
    public void doInPark() {
        if (entryCount == maxEntryCount) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + displayPrice);
        }
        if (!allowedEntryTimeType.isAllowedEntry(LocalTime.now())) {
            // #1on1: いいね、例外メッセージに allowedEntryTime を入れてデバッグ用の情報を出力している (2026/10/13)
            // jfluteだったら、もうentryTimeも両方出しちゃう。(step7とかでも話する予定)
            throw new IllegalStateException("Entry is not allowed at this time=" +
                    allowedEntryTimeType.getStartTime() + "~" + allowedEntryTimeType.getEndTime());
        }
        ++entryCount;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getDisplayPrice() {
        return displayPrice;
    }

    public int getMaxEntryCount() {
        return maxEntryCount;
    }

    public TicketBooth.AllowedEntryTimeType getAllowedEntryTimeType() {
        return allowedEntryTimeType;
    }

    public boolean isFullyUsed() {
        return entryCount == maxEntryCount;
    }
}
