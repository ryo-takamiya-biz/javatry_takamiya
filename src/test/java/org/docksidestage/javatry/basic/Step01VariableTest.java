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

import java.math.BigDecimal;

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of variable. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author takamiya
 */
public class Step01VariableTest extends PlainTestCase {

    // ===================================================================================
    //                                                                      Local Variable
    //                                                                      ==============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_variable_basic() { // example, so begin from the next method
        String sea = "mystic";
        log(sea); // your answer? => mystic
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_initial() {
        String sea = "mystic";
        Integer land = 8;
        String piari = null;
        String dstore = "mai";
        sea = sea + land + piari + ":" + dstore;
        log(sea); // your answer? => mystic8:mai(☓) => mystic8null:mai(o)
        // Integer型はstr型に自動でキャストされるかつnullは空文字と同じ扱いという予想
        // nullは"null"が出力される。printでも"null"が出力される。
        // #1on1: プログラミング言語の決め (2026/07/27)
        // C#だと、空文字が出力される。
        // "null" のメリデメ:
        // o デメリット: 本番で画面にnullって表示される可能性がある (メールの文言も)
        // o メリット: 空文字とnullの違いの見分けられる(by たかみやさん)
        //            ログでnullって出てわかりやすい、開発時は画面にnullって出てわかりやすい
        // ログはnullって出るのはいいけど、print (System.out.println()) で出るのは？
        // Javaだと、println (コンソール) もログの出力先の一つという感覚。
        // 開発時は "null" で本番は空文字とか？
        // 些細な違いでも、メリデメを考える習慣が大事。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_basic() {
        String sea = "mystic"; // 1(mystic)
        String land = "oneman"; // 2(oneman)
        sea = land;
        land = land + "'s dreams"; // 3('s dreams), 4(oneman's dreams)
        log(sea); // your answer? => oneman(o)
        // sea = land は文字列を上書きしている（参照先の代入ではない）のでlandの中身が変わってもseaにまで反映されないという予想
        // オブジェクトでは参照渡しらしい
        // done takamiya [いいね] オブジェクト型の変数は参照(アドレス)を持っているだけなのでGood by jflute (2026/07/27)
        // #1on1: オブジェクト型とプリミティヴ型の変数としての違い (2026/07/27)
        // #1on1: インスタンスとは？ (2026/07/27)
        // クラス (テンプレート) に対して、実際の一個ものを作ったもの by たかみやさん
        // 一軒家の例。
        // インスタンスエクササイズ。BigDecimalのadd()も見てみた。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_int() {
        int sea = 94;
        int land = 415;
        sea = land;
        land++;
        log(sea); // your answer? => 415(o)
        // 先程と同様にsea = landは値を上書きしている（参照先の代入ではない）のでlandの中身が変わってもseaにまで反映されないという予想
        // done takamiya 一方で、プリミティヴ型の変数は、値そのものを持っているイメージでOK by jflute (2026/07/27)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_BigDecimal() {
        BigDecimal sea = new BigDecimal(94); // 1
        BigDecimal land = new BigDecimal(415); // 2
        sea = land;
        sea = land.add(new BigDecimal(1)); // 3, 4
        sea.add(new BigDecimal(1)); // 5, 6
        log(sea); // your answer? => 417(☓) => 416(o)
        // 多分オブジェクトっぽいので参照渡しになっている予想
        // 5行目は返り値が+1された状態で返されるだけでsea自体が+1されるわけではなかった
        // done takamiya yes, 1回目のadd()で戻り値を戻しているところがポイントです by jflute (2026/07/27)
        // done jflute ここは1on1にてじっくりフォロー予定 (2026/07/27)
        // #1on1: add()メソッドのコードリーディング (2026/07/27)
        // #1on1: immutableとは？ (2026/07/27)
        // immutableは不変、BigDecimalはimmutable。そういう実装になっている。
        // クラス、immutable/mutableと二つある。
        // o インスタンスのimmutable
        // o 変数のimmutable
        //
        // immutableのメリデメ:
        // (メリット)
        // o 絶対に変わらないので、決まった定数とか変わらない前提、安全性 by たかみやさん
        // o プログラムの中で動かないことが大事 by たかみやさん
        // o 変わってないことが確定することで、読み飛ばせるとか可読性につながる
        // 安全は可読性につながる(可能性が高い) by jflute
        //
        // (デメリット)
        // TODO jflute 次回1on1にて、デメリットも出してみましょう (2026/07/27)
    }

    // ===================================================================================
    //                                                                   Instance Variable
    //                                                                   =================
    private String instanceBroadway;
    private int instanceDockside;
    private Integer instanceHangar;
    private String instanceMagiclamp;

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_String() {
        String sea = instanceBroadway;
        log(sea); // your answer? => null(o)
        // まだなにも入っていないのでnull
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_int() {
        int sea = instanceDockside;
        log(sea); // your answer? => null(☓) => 0(o)
        // intの場合には定義後はnullではなく0
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_Integer() {
        Integer sea = instanceHangar;
        log(sea); // your answer? => null(o)
        // オブジェクト型はnull, Integerはintのラッパーらしい
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_via_method() {
        instanceBroadway = "bbb";
        instanceMagiclamp = "magician";
        helpInstanceVariableViaMethod(instanceMagiclamp);
        String sea = instanceBroadway + "|" + instanceDockside + "|" + instanceHangar + "|" + instanceMagiclamp;
        log(sea); // your answer? => bigband|1|null|magician(o)
        // instanceBroadway:クラス内変数なのでhelpMethodの中での変更も反映されるのでbigband
        // instanceDockside:上と同じ理由で1
        // instanceHangar:特に変わらずnull
        // instanceMagiclamp:引数で渡したものは関数内の変数となる。helpMethod内では関数内の変数として認識されると思うので
        // 変わらないという予想でnull
        // TODO takamiya [いいね] 変数(という箱)自体が引数で渡されるわけではなく、参照が渡されるだけで... by jflute (2026/07/27)
        // 呼び出し側の変数と、引数を受け取るための変数は、別物(の箱)ということですね。
    }

    private void helpInstanceVariableViaMethod(String instanceMagiclamp) {
        instanceBroadway = "bigband";
        ++instanceDockside;
        instanceMagiclamp = "burn";
    }

    // ===================================================================================
    //                                                                     Method Argument
    //                                                                     ===============
    // -----------------------------------------------------
    //                                 Immutable Method-call
    //                                 ---------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_immutable_methodcall() {
        String sea = "harbor";
        int land = 415;
        helpMethodArgumentImmutableMethodcall(sea, land);
        log(sea); // your answer? => harbor(o)
        // help関数内では関数内変数を変更しているのでtest関数のseaはそのまま
    }

    private void helpMethodArgumentImmutableMethodcall(String sea, int land) {
        ++land;
        String landStr = String.valueOf(land);
        sea.concat(landStr);
    }

    // -----------------------------------------------------
    //                                   Mutable Method-call
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_mutable_methodcall() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentMethodcall(sea, land);
        log(sea); // your answer? => harbor416(o)
        // StringBuilderはオブジェクトなので引数でも参照渡しなのでhelp関数内での変更も反映される予想
        // TODO takamiya 厳密には「mutableなオブジェクトなので」ということですね by jflute (2026/07/27)
    }

    private void helpMethodArgumentMethodcall(StringBuilder sea, int land) {
        ++land;
        sea.append(land);
    }

    // -----------------------------------------------------
    //                                   Variable Assignment
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_variable_assignment() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentVariable(sea, land);
        log(sea); // your answer? => harbor(o)
        // test関数内で作成されたオブジェクトに対して、help関数内では何もしていないので変わらない
        // TODO takamiya [いいね] その通り、別のオブジェクト(インスタンス)のメソッドを呼んでるだけですね by jflute (2026/07/27)
    }

    private void helpMethodArgumentVariable(StringBuilder sea, int land) {
        ++land;
        String seaStr = sea.toString(); // is "harbor"
        sea = new StringBuilder(seaStr).append(land);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Define variables as followings:
     * <pre>
     * o local variable named sea typed String, initial value is "mystic"
     * o local variable named land typed Integer, initial value is null
     * o instance variable named piari typed int, without initial value
     * o show all variables by log() as comma-separated
     * </pre>
     * (変数を以下のように定義しましょう):
     * <pre>
     * o ローカル変数、名前はsea, 型はString, 初期値は "mystic"
     * o ローカル変数、名前はland, 型はInteger, 初期値は null
     * o インスタンス変数、名前はpiari, 型はint, 初期値なし
     * o すべての変数をlog()でカンマ区切りの文字列で表示
     * </pre>
     */
    private int piari;

    public void test_variable_writing() {
        String sea = "mystic";
        Integer land = null;
        log(sea + "," + land + "," + piari);
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Make your original exercise as question style about variable. <br>
     * (変数についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     * メソッド終了時の変数 sea の中身は？
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    private StringBuilder sea;

    public void test_variable_yourExercise() {
        helpMethod();
        log(sea);
        // helpMethod内で作ったオブジェクトではスコープがどうなるのか（残るのか消えるのか）という問題
    }

    private void helpMethod() {
        sea = new StringBuilder("abc");
    }
}
