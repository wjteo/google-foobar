package level3.challenge1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SolutionB {

    public static int solution(int[] l) {
        class Path extends ArrayList<Integer>{
            public Path(){
                super();
            }

            public Path(Integer start){
                super(Arrays.asList(start));
            }

            public Path(Path path){
                super(path);
            }
        }

        //Group of paths ending with the same integer, each path is mapped to its count
        class PathGroup extends HashMap<Path,Integer>{
            Integer group; //all paths should end with this integer
            public PathGroup(Integer group){
                super();
                this.group=group;
            }

            //returns a group of paths that needs to be updated
            HashMap<Path,Integer> addToPaths(Integer num, HashMap<Path,Integer> updates){
                if(num.intValue()==group.intValue()){
                    updates.put(new Path(num),1);
                }
                super.forEach((path,count)->{
                    Path newPath = new Path(path);
                    newPath.add(num);
                    Integer currentIncrement=updates.getOrDefault(newPath,0);
                    updates.put(newPath, currentIncrement+count);
                });
                return updates;
            }

            void incrementPathCount(Path path,Integer increment){
                Integer currentCount=super.getOrDefault(path,0);
                super.put(path,currentCount+increment);
            }
        }

        class PathData {
            public int completedPathsCount;
            //Paths grouped by the ending integer, each path is at most size 2
            public HashMap<Integer,PathGroup> pathGroups;
            public PathData(){
                super();
                this.completedPathsCount=0;
                this.pathGroups =new HashMap<>();
            }

            public void insert(int num){
//                System.out.println("-------------------------");
//                System.out.println("processing number:" +num);

                //insert new key if key is not yet present
                if(pathGroups.get(num)==null){
                    pathGroups.put(num,new PathGroup(num));
                }

                PathGroup thisNumPathGroup = pathGroups.get(num);

                HashMap<Path,Integer> updates = new HashMap<>();
                pathGroups.forEach((group,pathGroup)->{
                    if(num%group==0){
                        pathGroup.addToPaths(num,updates);
                    }
                });
                updates.forEach((path,increment)->{
//                    System.out.println("update path:" + path + " by " + increment);
                    if(path.size()==3){
                        completedPathsCount+=increment;
//                        System.out.println("completed:" +completedPathsCount);
                    }else{
                        thisNumPathGroup.incrementPathCount(path,increment);
                    }
                });

//                logging only
//                pathGroups.forEach((group,pathGroup)->{
//                    System.out.println("End: "+pathGroup);
//                });

            }

        }

        PathData pathData = new PathData();

        for(int i=0;i<l.length;i++){
            pathData.insert(l[i]);
        }

//        System.out.println("completedPaths: "+ pathData.completedPathsCount);
//        Set<Integer> keySet=pathData.pathGroups.keySet();
//        System.out.println("pathGroups: ");
//        for(Integer i: keySet){
//            System.out.println(i+":"+pathData.pathGroups.get(i));
//        }
        return pathData.completedPathsCount;
    }

}