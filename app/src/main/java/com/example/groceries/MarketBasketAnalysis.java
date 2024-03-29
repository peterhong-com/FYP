package com.example.groceries;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class MarketBasketAnalysis {
    List<List<String>> dataToAnalyse;
    List<List<String>> dataToAnalyse1;
    List<List<String>> dataToAnalyse2;
    List<String> uniqueItemList;
    List<String> uniqueItemList1;
    List<String> uniqueItemList2;
    long numberOfTransactions;
    static String d[][];
    static String item="";
    InputStreamReader sss;
    static ArrayList<String> key=new ArrayList<>();
    Set<Map<String, Map<String,Double>>> dw =new HashSet<>();
    Map<String, Map<String,Double>> num=new HashMap<String,Map<String,Double>>();
    Map<String,Double> answer=new HashMap<String,Double>();
    ArrayList<String> shoppingList = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.N)
    public MarketBasketAnalysis(InputStreamReader ss) throws IOException {

        sss=ss;
        BufferedReader bufferedReader =
                new BufferedReader(sss);



        String line = null;


        while((line = bufferedReader.readLine()) != null) {

            shoppingList.add(line);
        }
        dataToAnalyse = getSeparatedData(shoppingList);

        numberOfTransactions = (long) dataToAnalyse.size();
        uniqueItemList = dataToAnalyse.stream().flatMap(List::stream).distinct().collect(Collectors.toList());

        bufferedReader.close();
    }
    public String returne(String sss) throws IOException {



        item=sss;
        dw=runApriori();
        int count2=0;

        Double[] sa=new Double[100000];
        Double[] se= new Double[100000];
        String ss= String.valueOf(sss);
        String[] ds= new String[100000];
        ArrayList<Double> s = new ArrayList<Double>();
        for (Map<String,Map<String,Double>> a: dw) {

            if(a.containsKey(String.valueOf(ss))) {

                s.add(a.get(ss).get(d[0][count2]));
                sa[count2]=a.get(ss).get(d[0][count2]);
                se[count2]=a.get(ss).get(d[0][count2]);
                ds[count2]=d[0][count2];
                count2++;

            }
        }

        if(s.size()==0) {
            return "-1";
        }

        int find=0;
        int find1=0;
        for(int i = 0 ; i<sa.length-1;i++) {
            if(sa[i]==null||sa[i+1]==null) {
                break;
            }
            if(sa[find1]<sa[i+1]) {
                find1=i+1;

            }
        System.out.println(sa[i]+ds[i]);
        }
        for(int i = 0 ; i<sa.length-1;i++) {
            if(se[i]==sa[find1]) {
                find=i;
                break;
            }
        }
        return ds[find];

    }
    public Set<Map<String, Map<String,Double>>> runApriori() {
//		calculate support for every item
        int count1=0;
        int count2=0;
        d=new String[10000000][];
        double support = calculateSupport(item);
        count1=0;
        count2=0;
        d[count1] = new String[10000000];
        answer=new HashMap<>();
        num=new HashMap<>();

        for (String itemSecond : uniqueItemList) {

            if (!item.equals(itemSecond)) {

                double confidence = calculateConfidence(item, itemSecond);
                answer.put(itemSecond, confidence/support);
                num.put(item, answer);
                d[0][count2]=itemSecond;

                dw.add(num);

                count2++;
            }

            if(!key.contains(item)) {
                key.add(item);
            }
            count1++;

        }

//		calculate confidence item1 -> item2
//		A, B, C, E, Z
//
//		A -> B, A -> C, A -> E, A -> Z
//		B -> A, B -> C, B -> E, B -> Z
//		C -> A, C -> B, C -> E, C -> Z
//		E -> A, E -> B, E -> C, E -> Z
//		Z -> A, Z -> B, Z -> C, Z -> E



        return dw;
    }

    /**
     * @param itemFirst
     * @param itemSecond
     * @return
     */
    private double calculateConfidence(String itemFirst, String itemSecond) {
        double confidence = 0.0;
        if (itemFirst != null && itemSecond != null) {
//			calculate transactions with both items
            long howManyTimesBothItemsAppear = 0;
            long howManyTimesFirstItemAppear = 0;
            for (List<String>itemList : dataToAnalyse) {
                if (itemList.contains(itemFirst)) {
                    howManyTimesFirstItemAppear++;
                    if (itemList.contains(itemSecond)) {
                        howManyTimesBothItemsAppear++;
                    }
                }
            }
            confidence = (double) howManyTimesBothItemsAppear / howManyTimesFirstItemAppear;
        }
        return confidence;
    }

    private double calculateSupport(String item) {
        double support = 0.0;
        if (item != null) {
            long howManyTimes = 0;
            for (List<String> transactionItemsList : dataToAnalyse) {
                boolean listContainsItem = false;
                if (!listContainsItem && transactionItemsList.contains(item)) {
                    howManyTimes++;
                    listContainsItem = true;
                }
            }
            support = (double) howManyTimes / (double) numberOfTransactions;
        }
        return support;
    }




    private List<List<String>> getSeparatedData(List<String> shoppingOrderList) {
        List<List<String>> listOfTrimmedData = new ArrayList<>();
        for (int i=0; i<shoppingOrderList.size();i++) {
            listOfTrimmedData.add(Arrays.asList(shoppingOrderList.get(i).split( (","))));
        }

        return listOfTrimmedData;
    }

    private List<List<String>> getSeparatedData1(List<String> shoppingOrderList) {
        List<List<String>> listOfTrimmedData = new ArrayList<>();
        for (int i=0; i<shoppingOrderList.size()/3; i++) {
            listOfTrimmedData.add(Arrays.asList(shoppingOrderList.get(i+shoppingOrderList.size()/3).split( (","))));
        }

        return listOfTrimmedData;
    }

    private List<List<String>> getSeparatedData2(List<String> shoppingOrderList) {
        List<List<String>> listOfTrimmedData = new ArrayList<>();
        for (int i =0; i<shoppingOrderList.size()/3;i++) {
            listOfTrimmedData.add(Arrays.asList(shoppingOrderList.get(i+shoppingOrderList.size()/3*2).split( (","))));
        }

        return listOfTrimmedData;
    }
    class Multi extends Thread{
        public void run(){
            System.out.println("Transactions: " + numberOfTransactions);
            System.out.println("All items: " + uniqueItemList);
//	calculate support for every item
            int count1=0;
            int count2=0;



            double support = calculateSupport(item);

            System.out.println("Support for item: \'" + item + "\'");
            System.out.println("Is equal to: " + support);


            count1=0;
            count2=0;
            d[count1] = new String[100000];
            answer=new HashMap<>();
            num=new HashMap<>();
            Multi t1=new Multi();
            t1.start();
            for (String itemSecond : uniqueItemList1) {

                if (!item.equals(itemSecond)) {

                    double confidence = calculateConfidence(item, itemSecond);


                    answer.put(itemSecond, confidence/support);
                    num.put(item, answer);
                    d[0][uniqueItemList.size()]=itemSecond;

                    dw.add(num);

                    count2++;
                }

                if(!key.contains(item)) {
                    key.add(item);
                }
                count1++;

            }

        }
    }
    class Multi1 extends Thread{
        public void run(){
            System.out.println("Transactions: " + numberOfTransactions);
            System.out.println("All items: " + uniqueItemList);
//	calculate support for every item
            int count1=0;
            int count2=0;
            d=new String[shoppingList.size()*shoppingList.size()][];


            double support = calculateSupport(item);

            System.out.println("Support for item: \'" + item + "\'");
            System.out.println("Is equal to: " + support);


            count1=0;

            d[count1] = new String[100000];
            answer=new HashMap<>();
            num=new HashMap<>();
            Multi t1=new Multi();
            t1.start();
            for (String itemSecond : uniqueItemList2) {

                if (!item.equals(itemSecond)) {

                    double confidence = calculateConfidence(item, itemSecond);


                    answer.put(itemSecond, confidence/support);
                    num.put(item, answer);
                    d[0][uniqueItemList.size()*2]=itemSecond;

                    dw.add(num);

                    count2++;
                }

                if(!key.contains(item)) {
                    key.add(item);
                }
                count1++;

            }

        }
    }
}
