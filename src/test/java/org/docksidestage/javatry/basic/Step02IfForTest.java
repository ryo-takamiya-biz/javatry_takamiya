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
package org.docksidestage.javatry.basic;

import java.util.ArrayList;
import java.util.List;

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of if-for. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author takamiya
 */
public class Step02IfForTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        if Statement
    //                                                                        ============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_if_basic() { // example, so begin from the next method
        int sea = 904;
        if (sea >= 904) {
            sea = 2001;
        }
        log(sea); // your answer? => 2001
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_else_basic() {
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else {
            sea = 7; // ここに入る
        }
        log(sea); // your answer? => 7(○)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_elseif_basic() {
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else if (sea >= 904) {
            sea = 7; // ここに入る
        } else if (sea >= 903) {
            sea = 8;
        } else {
            sea = 9;
        }
        log(sea); // your answer? => 7(○)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_elseif_nested() {
        boolean land = false;
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
            sea = sea++ * 2;
        } else if (land && sea >= 904) {
            sea = 7;
            sea = ++sea * 2;
        } else if (sea >= 903 || land) {
            if (sea % 2 == 0) {
                sea = sea++ * 2; // ここに入る → 1808
            }
            if (!land) {
                land = true; // ここに入る
            } else if (sea <= 903) {
                sea++;
            }
            if (sea < 1810) {
                sea = 8; // ここに入る
            }
        } else if (sea == 8) {
            sea++;
            land = false;
        } else {
            sea = 9;
        }
        if (sea >= 9 || (sea > 7 && sea < 9)) {
            sea--; // ここに入る → 7
            if (sea % 2 == 1) {
                sea++; // ここに入る → 8
            }
        }
        if (land) {
            sea = 10; // ここに入る
        }
        log(sea); // your answer? => 8(☓) => 10(○)
        // なぜか最後のif文の中身を考えていなかった
        // 目だけで追うとちょっとつらい
        // done takamiya [いいね] 良い体験です(^^。その思い出が工夫につながります。 by jflute (2026/08/13)
        // #1on1: 目が疲れて小さなところを見逃してしまうミスをできるだけ防ぐために (2026/08/14)
        // 最初に漠然と読んで構造だけ把握して、頭の中でコードの地図を作ってから、詳細を読む。
        // すると、自分の位置を理解しながら読み進めていくことになるので、比較的見逃しも少なくなる。
        //
        // (漫画の例)
        // 楽しく読むと、効率よく読むはちょっと違う。

        // TODO jflute 1on1にて、漠然読みのお話をする予定 (2026/08/13)
    }

    // ===================================================================================
    //                                                                       for Statement
    //                                                                       =============
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_inti_basic() {
        List<String> stageList = prepareStageList(); // {"broadway", "dockside", "hangar", "magiclamp"}
        String sea = null;
        for (int i = 0; i < stageList.size(); i++) {
            String stage = stageList.get(i);
            if (i == 1) { // 1番目をseaに格納
                sea = stage;
            }
        }
        log(sea); // your answer? => "dockside"(○)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_foreach_basic() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (String stage : stageList) {
            sea = stage; // 毎回seaに格納
        }
        log(sea); // your answer? => "magiclamp"(○)

        // done jflute 1on1にて、Java文法としてのfor文のお話 (2026/08/13)
        // #1on1: for文いっぱい (2026/08/14)
        // $ 使い分けどうするんだろう？って思った by たかみやさん
        // o intあいのfor文  // Java文法、C言語から伝わる伝統的なループ、Java当初から
        // o 拡張for文 (foreach文, 普通のfor文) // Java文法、もっとすっきり書ける、Java10年くらいから
        // → ここでは、使い分けはほとんど存在せず、ほぼ拡張for文 (現場感覚値)
        //
        // o forEach()メソッド // 単なるメソッド、Java20年くらいから
        // done jflute ↑は、forEach()メソッドへの置き換えエクササイズやってもらってから (2026/08/14)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_foreach_continueBreak() {
        List<String> stageList = prepareStageList(); // {"broadway", "dockside", "hangar", "magiclamp"}
        String sea = null;
        for (String stage : stageList) {
            if (stage.startsWith("br")) { // brから始まるか
                continue; // 0番目のみ
            }
            sea = stage;
            if (stage.contains("ga")) { // gaが含まれるか
                break; // 2番目
            }
        }
        log(sea); // your answer? => "hangar"(○)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_listforeach_basic() {
        List<String> stageList = prepareStageList();
        StringBuilder sb = new StringBuilder();
        stageList.forEach(stage -> {
            if (sb.length() > 0) {
                return; // "dockside"後に出る
            }
            if (stage.contains("i")) {
                sb.append(stage); // "dockside"で入る
            }
        });
        String sea = sb.toString();
        log(sea); // your answer? => "dockside"(○)
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Make list containing "a" from list of prepareStageList() and show it as log by loop. (without Stream API) <br>
     * (prepareStageList()のリストから "a" が含まれているものだけのリストを作成して、それをループで回してログに表示しましょう。(Stream APIなしで))
     */
    public void test_iffor_making() {
        // write if-for here
        List<String> stageList_contain_a = new ArrayList<>();
        for (String stage : prepareStageList()) {
            if (stage.contains("a")) {
                stageList_contain_a.add(stage);
            }
        }
        for (String stage : stageList_contain_a) {
            log(stage);
        }
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Change foreach statement to List's forEach() (keep result after fix) <br>
     * (foreach文をforEach()メソッドへの置き換えてみましょう (修正前と修正後で実行結果が同じになるように))
     */
    public void test_iffor_refactor_foreach_to_forEach() {
        // done takamiya もし、stageListにbongarという要素が追加されても結果は同じになるか？ by jflute (2026/08/26)
        // 手段は問わず互換性を維持してみましょう。
        // ver2
        // #1on1: gaを含むものが二つあっても、最初のgaだけが残るようになっているGood (2026/09/08)
        // done takamiya "ga" を含むものが一つもなかったときに以前と結果が同じになるか？ by jflute (2026/09/08)
        List<String> stageList = prepareStageList();
        String sea = null;
        String[] seaList = new String[1];
        stageList.forEach(stage -> {
            if (stage.startsWith("br")) {
                return;
            }
            if (stage.contains("ga") || seaList[0] == null || !seaList[0].contains("ga")) {
                seaList[0] = stage;
            }
        });
        sea = seaList[0];
        log(sea); // should be same as before-fix
        // ver1
        //        List<String> stageList = prepareStageList();
        //        String sea = null;
        //        StringBuilder seaBuilder = new StringBuilder();
        //        stageList.forEach(stage -> {
        //            if (stage.startsWith("br")) {
        //                return;
        //            }
        //            // 読み取り専用の変数じゃないと Lambda式{}の中では利用できない (実質変更できない)
        //            //sea = stage;
        //            if (stage.contains("ga")) {
        //                seaBuilder.append(stage);
        //            }
        //        });
        //        sea = seaBuilder.toString();
        //        log(sea); // should be same as before-fix
        // 中でseaが使えない（ローカル変数は実質的にfinalでないといけない）、返り値も受け取れない
        // 自力ではできなかった...
        // 可変オブジェクトなら変更できるとわかったので、StringBuilderを経由した

        // #1on1: なぜseaを使えないのか？ (2026/08/26)
        // $Lambda式の{}が(別の)関数みたいなものだから!? (by たかみやさん)
        // まさしくその直感通りで、{}の中は別のクラス別のメソッドみたいなもの。
        // forEach()のコードリーディングしてみると...
        // Javaの文法としてのfor文をただ肩代わりしているだけの普通のメソッド。
        // Lambda式とConsumerインターフェースのちょい先取り。じっくりはstep8にて。
        // なんにせよ、{}のところは、別のクラスをnewして引数に入れているだけという感覚。
        // $質問: 名前付きクラスをnewして入れることもできるか？
        // できる。ライブコーディングで new Abc() してみた。
        // $もち回すことがしやすいのがメリット？
        // for文でもメソッド化できなくはないけど、Lambdaの方がオブジェクトになってるので扱いやすいというのはある。
        // forEach()は、ただのfor文の代理人みたいな感じ。
        //
        // だから、別のメソッドが、別のメソッドのローカル変数を書き換えることができたら...大変。
        // プログラミング言語の決めではあるが、カオスを生み出さないために制限を掛けている。
        // ローカル変数はあくまでローカル変数なので、他のメソッドからは変更できないように。
        //
        // ただ、(条件付きで)参照だけはOK。参照は、変数の中身をコピーしてしまえば管理を分離できるから。
        // 条件というが、immutableな変数であること。変わらなければ、参照のカオスは起きない。
        //
        // 実質的finalとは？のお話。
        //
        // return; が使えるのは、{} がメソッドだから。
        // continue; が使えないのは、{} が単なるメソッドだから。(for文の直下の処理じゃないから)
        //
        // なので、forEach()メソッドは、外側変数の代入もできないし、continue/breakもできない。
        // できないことが多いループ。
        //
        // o intあいfor文      : Java当初から(1995年)
        // o 拡張for文         : 10年後くらいにできた(2005年くらい)
        // o forEach()メソッド : 20年後くらいにできた(2015年くらい)
        //
        // $ストレートなループ処理の時だけ使う？
        // yes, webサービスの世界だと、ループってストレートに回すだけのことが圧倒的に多い。
        // 制限を掛けることで得られるものがある。
        // $immutableのときの話と同じ？
        // yes, 安全性と可読性
        //
        // 拡張for文: mutableなループなイメージ
        // forEach()メソッド: immutableなループなイメージ
        //
        // よもやま: 極論のお話
        //
        // mutableなオブジェクトでforEach()メソッドを台無しにできる。
        // (だからこそ、オブジェクトもimmutableに寄せようと言う考えもある)
        //
        // 適材適所すぎるのもつらいのでジレンマ。

        /* 元の一番最初の状態:
        List<String> stageList = prepareStageList();
        String sea = null;
        for (String stage : stageList) {
            if (stage.startsWith("br")) {
                continue;
            }
            sea = stage;
            if (stage.contains("ga")) {
                break;
            }
        }
        log(sea); // should be same as before-fix
         */
    }

    /**
     * Make your original exercise as question style about if-for statement. <br>
     * (if文for文についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     * logの出力結果は？
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    public void test_iffor_yourExercise() {
        List<String> stageList = prepareStageList();
        int i = 0;
        stageList.forEach(stage -> {
            if (stage.contains("ng")) {
                stage = stage + "abc";
            }
        });
        stageList.forEach(stage -> {
            log(stage);
        });
    }

    // ===================================================================================
    //                                                                        Small Helper
    //                                                                        ============
    private List<String> prepareStageList() {
        List<String> stageList = new ArrayList<>();
        stageList.add("broadway");
        stageList.add("dockside");
        stageList.add("hangar");
        stageList.add("magiclamp");
        return stageList;
    }
}
