package level5.challenge1;

import java.util.*;

public class Solution {

    public static final List<Table>trueExpandedTables=new ArrayList<>(Arrays.asList(
            new Table(new boolean[][]{{false,false},{false,true}}),
            new Table(new boolean[][]{{false,false},{true,false}}),
            new Table(new boolean[][]{{false,true},{false,false}}),
            new Table(new boolean[][]{{true,false},{false,false}})
    ));

    public static final List<Table>falseExpandedTables=new ArrayList<>(Arrays.asList(
            new Table(new boolean[][]{{false,false},{false,false}}),
            new Table(new boolean[][]{{false,false},{true,true}}),
            new Table(new boolean[][]{{false,true},{false,true}}),
            new Table(new boolean[][]{{true,false},{false,true}}),
            new Table(new boolean[][]{{false,true},{true,false}}),
            new Table(new boolean[][]{{true,false},{true,false}}),
            new Table(new boolean[][]{{true,true},{false,false}}),
            new Table(new boolean[][]{{false,true},{true,true}}),
            new Table(new boolean[][]{{true,false},{true,true}}),
            new Table(new boolean[][]{{true,true},{false,true}}),
            new Table(new boolean[][]{{true,true},{true,false}}),
            new Table(new boolean[][]{{true,true},{true,true}})
    ));

    public static class Table {
        boolean[][] values;
        public int height;
        public int width;
        Row[] rows;
        Column[] columns;


        public Table(boolean[][] values){
            this.values=values;
            if(values.length==0){
                //should not come here
                return;
            }
            this.height=values.length;
            this.width=values[0].length;
            this.rows=new Row[height];
            this.columns=new Column[width];
            for(int rowIndex=0; rowIndex<this.height;rowIndex++){
                Row row=new Row();
                for(int colIndex=0; colIndex<this.width;colIndex++){
                    row.push(values[rowIndex][colIndex]);
                }
                this.rows[rowIndex]=row;
            }
            for(int colIndex=0; colIndex<this.width;colIndex++){
                Column col=new Column();
                for(int rowIndex=0; rowIndex<this.height;rowIndex++){
                    col.push(values[rowIndex][colIndex]);
                }
                this.columns[colIndex]=col;
            }
        }

        public Row getRow(int index){
            return rows[index];
        }

        public Row getLastRow(){
            return rows[this.height-1];
        }

        public Column getColumn(int index){
            return columns[index];
        }

        public Column getLastColumn(){
            return columns[this.width-1];
        }

        @Override
        public boolean equals(Object table){
            return this.values.equals(((Table)table).values);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(this.values);
        }
    }

    public static class Row {
        public ArrayList<Boolean> values;
        public int length;
        public Row(){
            this.values=new ArrayList<>();
        }

        public void push(Boolean b){
            this.values.add(b);
            this.length++;
        }

        public Boolean get(int index){
            return this.values.get(index);
        }

        @Override
        public boolean equals(Object row){
            return this.values.equals(((Row)row).values);
        }

        @Override
        public int hashCode(){
            return this.values.hashCode();
        }
    }

    public static class Column {
        public ArrayList<Boolean> values;
        public int length;
        public Column(){
            this.values=new ArrayList<>();
        }

        public void push(Boolean b){
            this.values.add(b);
            this.length++;
        }

        public Boolean get(int index){
            return this.values.get(index);
        }

        @Override
        public boolean equals(Object col){
            return this.values.equals(((Column)col).values);
        }

        @Override
        public int hashCode(){
            return this.values.hashCode();
        }
    }


    public static int solution(boolean[][] g) {
        Table inputTable = new Table(g);
        Column firstColumn = inputTable.getColumn(0);
        List<Table> possibilities=computeExpandedColumns(firstColumn);
        Map<Column,Integer> countsMap = countsGroupByLastColumn(possibilities);

        //subsequent columns
        for(int colIndex=1;colIndex<inputTable.width;colIndex++){
            Column column=inputTable.getColumn(colIndex);
            List<Table> tableList=computeExpandedColumns(column);
            countsMap=horizontalMergeAndCount(countsMap,tableList);
        }

        Set<Column> columns= countsMap.keySet();
        int sum=0;
        for(Column col: columns){
            sum+=countsMap.get(col);
        }
        return sum;
    }

    public static Map<Column,Integer> horizontalMergeAndCount(Map<Column,Integer> countsMap, List<Table>tableList){
        Map<Column,Integer> newCountsMap= new HashMap<>();
        for(Table table: tableList){
            Column firstColumn=table.getColumn(0);
            Column secondColumn=table.getColumn(1);
            if(countsMap.containsKey(firstColumn)){
                if(newCountsMap.containsKey(secondColumn)){
                    int newCount=newCountsMap.get(secondColumn)+countsMap.get(firstColumn);
                    newCountsMap.put(secondColumn,newCount);
                }else{
                    newCountsMap.put(secondColumn,countsMap.get(firstColumn));
                }
            }
        }
        return newCountsMap;
    }

    public static Map<Column,Integer> countsGroupByLastColumn(List<Table> possibilities){
        Map<Column,Integer> countsMap=new HashMap<>();
        for(Table table:possibilities){
            Column lastColumn=table.getLastColumn();
            if(countsMap.containsKey(lastColumn)){
                Integer count = countsMap.get(lastColumn);
                countsMap.put(lastColumn,++count);
            }else{
                countsMap.put(lastColumn,1);
            }
        }
        return countsMap;
    }
    public static List<Table> computeExpandedColumns(Column column){
        List<Table> tableList=column.get(0)?trueExpandedTables:falseExpandedTables;
        for(int i=1;i<column.length;i++){
            List<Table> nextElementTableList=column.get(i)?trueExpandedTables:falseExpandedTables;
            tableList = verticalMergeAndFilter(tableList, nextElementTableList);
        }
        return tableList;
    }

    public static List<Table> verticalMergeAndFilter(List<Table> topList,List<Table> bottomList){
        List<Table> filtered= new ArrayList<>();
        for(Table top:topList){
            for(Table bottom:bottomList){
                if(top.getLastRow().equals(bottom.getRow(0))){
                    boolean[][]merged=new boolean[top.height+bottom.height-1][top.width];
                    System.arraycopy(top.values,0,merged,0,top.height);
                    System.arraycopy(bottom.values,1,merged,top.height,bottom.height-1);
                    filtered.add(new Table(merged));
                }
            }
        }
        return filtered;
    }

}



