package level3.challenge1;

import java.util.*;


public class Solution {

    public static int solution(int[] l) {
        //Paths mapped by number at the end
        class Paths {
            public HashSet<ArrayList<Integer>> completedPaths;
            public HashMap<Integer,HashSet<ArrayList<Integer>>> incompletePaths;
            public Paths(){
                super();
                this.completedPaths=new HashSet<>();
                this.incompletePaths=new HashMap<>();
            }

            public void insert(int num){
                System.out.println("processing number:" +num);

                //insert new key if key is not yet present
                if(incompletePaths.get(num)==null){
                    incompletePaths.put(num,new HashSet<>());
                }

                //handle paths which ends with the same number
                HashSet<ArrayList<Integer>> pathSetEndingWithNum = incompletePaths.get(num);
                if(pathSetEndingWithNum.size()==0){
                    pathSetEndingWithNum.add(new ArrayList<>(Arrays.asList(num)));
                }else{
                    for(ArrayList<Integer> path:pathSetEndingWithNum){
                        if(path.size()==2){
                            ArrayList<Integer> newPath = new ArrayList<>(path);
                            newPath.add(num);
                            completedPaths.add(newPath);
                        }else{
                            path.add(num);
                        }
                    }
                }

                Set<Integer> pathEnds = incompletePaths.keySet();
                for(Integer pathEnd: pathEnds){
                  if(num<pathEnd){
                      continue;
                  }

                  if(num==pathEnd){
                      continue; //already handled
                  }
                  if(num%pathEnd!=0){
                      continue;
                  }
                  //for every path that is divisible, append to end of path
                  HashSet<ArrayList<Integer>> pathSet = incompletePaths.get(pathEnd);
                  for(ArrayList<Integer> path:pathSet){
                      ArrayList<Integer> newPath = new ArrayList<>(path);
                      newPath.add(num);
                      if(newPath.size()<3){
                          pathSetEndingWithNum.add(newPath);
                      }else{
                          completedPaths.add(newPath);
                      }
                  }
                }
            }

        }

        Paths paths = new Paths();

        for(int i=0;i<l.length;i++){
            paths.insert(l[i]);
        }

        System.out.println("completedPaths: "+ paths.completedPaths);
        Set<Integer> keySet=paths.incompletePaths.keySet();
        System.out.println("pathGroups: ");
        for(Integer i: keySet){
            System.out.println(i+":"+paths.incompletePaths.get(i));
        }
        return paths.completedPaths.size();
    }

}