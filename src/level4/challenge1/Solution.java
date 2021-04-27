package level4.challenge1;

import java.util.*;


public class Solution {
    public static class EdmondBlossom<T>{
        private Map<T,Node<T>> graph;

        public EdmondBlossom( Map<T,Node<T>> graph){
            this.graph = graph;
        }

        Map<Node,Node> getInitialBaseMap(){
            Map<Node,Node> initialBaseMap =new HashMap<>();
            for(T id: graph.keySet()){ //every node maps to itself
                initialBaseMap.put(graph.get(id),graph.get(id));
            }
            return initialBaseMap;
        }

        public void computeMaxMatch() {
            Set<T> nodeIds = graph.keySet();
            for(T nodeId : nodeIds){
                Node node=graph.get(nodeId);
                if(node.hasMatch()){
                    continue;
                }
                findAndAugmentPath(node);
            }
        }

        void updateMatches(AlternatingPath path){
            Node refNode=path.tail;
            boolean done=false;
            while(!done){
                Node nextNode=path.getNext(refNode);
                if(!path.hasMatchedNext(nextNode)){
                    done=true;
                    refNode.matchUp(nextNode);
                }else{
                    Node followingNode=nextNode.getMatch();
                    refNode.matchUp(nextNode);
                    refNode=followingNode;
                }
            }
        }

        void findAndAugmentPath(Node node) {
            AlternatingPath altPath = new AlternatingPath();
            Set<Node> visited= new HashSet<>();
            Map<Node,Node> baseMap=getInitialBaseMap();

            visited.add(node);
            LinkedList<Node> searchQueue = new LinkedList<>();
            searchQueue.addLast(node);
            Node<T> currentSearch;

            while(!searchQueue.isEmpty()){
                currentSearch=searchQueue.removeFirst();
                for (Node linkedNode : currentSearch.getLinks()) {
                    if (linkedNode == node || altPath.hasMatchedNext(linkedNode)) { //blossom detected
                        Node currentBase = currentSearch.getLCA(linkedNode,baseMap, altPath);
                        Set<Node> blossom = new HashSet<>();
                        markPath(baseMap, blossom, altPath, currentSearch, currentBase, linkedNode); //markPath and computeBlossom
                        markPath(baseMap, blossom, altPath, linkedNode, currentBase, currentSearch);
                        Set<T> nodeIds = graph.keySet();
                        for(T nodeId : nodeIds){
                            Node n=graph.get(nodeId);
                            if (!blossom.contains(baseMap.get(n))) {
                              continue;
                            }
                            baseMap.put(n,currentBase);
                            if (!visited.contains(n)) {
                                visited.add(n);
                                searchQueue.addLast(n);
                            }
                        }
                    }
                    else if (!altPath.isAlternateNode(linkedNode)) {
                        altPath.addOddPath(linkedNode,currentSearch);
                        if (!linkedNode.hasMatch()){ //exposed node found
                            altPath.setTail(linkedNode);
                            searchQueue.clear();
                            break;
                        }
                        linkedNode = linkedNode.getMatch();
                        visited.add(linkedNode);
                        searchQueue.addLast(linkedNode);
                    }
                }
            }
            if(altPath.isAugmenting()){
                updateMatches(altPath);
            }
        }

        void markPath(Map<Node,Node> baseMap, Set blossom,  AlternatingPath path, Node node, Node base,Node next) {
            while(baseMap.get(node)!=base){
                blossom.add(baseMap.get(node));
                blossom.add(baseMap.get(node.getMatch()));
                path.addOddPath(node,next);
                next=node.getMatch();
                node=path.getMatchedNext(node);
            }
        }

        public static class AlternatingPath{
            Map<Node,Node> alternatePathTree; //path between unmatched nodes;
            Node tail=null; //exposed node at the end of the path

            public AlternatingPath(){
                this.alternatePathTree=new HashMap<>();
            }

            void addOddPath(Node from, Node to){
                alternatePathTree.put(from,to);
            }
            //alternate nodes are nodes with a link to the next node (unmatched) in an alternating path
            boolean isAlternateNode(Node node){
                return alternatePathTree.containsKey(node);
            }

            Node getNext(Node from){
                return alternatePathTree.get(from);
            }

            Node getMatchedNext(Node from){
                if(from.hasMatch()){
                    return alternatePathTree.get(from.getMatch());
                }else{
                    return null;
                }
            }

            boolean hasMatchedNext(Node from){
                if(from.hasMatch()){
                    return alternatePathTree.containsKey(from.getMatch());
                }else{
                    return false;
                }
            }

            void setTail(Node node){
                this.tail=node;
            }

            boolean isAugmenting(){
                return this.tail!=null;
            }

        }

        public static class Node<T>{
            public T id;
            List<Node> linkedNodes;
            Node match=null;
            public Node(T id){
                this.id=id;
                this.linkedNodes=new ArrayList<>();
            }

            public void addLink(Node<T> node){
                this.linkedNodes.add(node);
            }

            List<Node> getLinks(){
                return this.linkedNodes;
            }

            public Node getMatch(){
                return match;
            }

            //add node as a match and updates corresponding nodes as well
            void matchUp(Node node){
                this.setMatch(node);
                if(node.hasMatch()){
                    Node hisMatch=node.getMatch();
                    hisMatch.setMatch(null);
                }
                node.setMatch(this);
            }

            void setMatch(Node node){
                this.match=node;
            }

            public boolean hasMatch(){
                return this.match!=null;
            }

            //lowest common ancestor
            Node getLCA(Node node, Map<Node,Node> baseMap, AlternatingPath path){
                Set<Node> myAncestors= new HashSet<>();
                boolean done = false;
                Node myAncestor=this;
                while(!done){
                    myAncestor = baseMap.get(myAncestor);
                    myAncestors.add(myAncestor);
                    if(path.hasMatchedNext(myAncestor)){
                        myAncestor=path.getMatchedNext(myAncestor);
                    }else{
                        done=true;
                    }
                }

                boolean found=false;
                Node hisAncestor=node;
                while(!found){
                    hisAncestor = baseMap.get(hisAncestor);
                    if (myAncestors.contains(hisAncestor)){
                        found=true;
                    }else{
                        hisAncestor = path.getMatchedNext(hisAncestor);
                    }
                }
                return hisAncestor;
            }
        }

    }

    public static int solution(int[] banana_list) {
        Map<Integer,EdmondBlossom.Node<Integer>> graph=computePossibleMatches(banana_list);

        new EdmondBlossom(graph).computeMaxMatch();
        int matchedCount=0;
        for(int nodeIds: graph.keySet()){
            EdmondBlossom.Node node=graph.get(nodeIds);
            if(node.hasMatch()){
                matchedCount++;
            }
        }
        return banana_list.length-matchedCount;
    }

    public static  Map<Integer,EdmondBlossom.Node<Integer>>  computePossibleMatches(int[] banana_list){
        Map<Integer,EdmondBlossom.Node<Integer>> graph= new HashMap<>();
        for(int i=0;i<banana_list.length;i++){
            graph.put(i,new EdmondBlossom.Node(i));
        }

        for(int i=0; i< banana_list.length-1;i++){
            for(int j=i+1; j<banana_list.length;j++){
                if(canMatch(banana_list[i],banana_list[j])){
                    graph.get(i).addLink(graph.get(j));
                    graph.get(j).addLink(graph.get(i));
                }
            }
        }
        return graph;
    }


    //returns true if the pair would result in an infinite loop
    public static boolean canMatch(int x, int y){

        if(x==y){
            return false;
        }

        //can match if sum is odd number
        if((x+y)%2==1){
            return true;
        }

        //either both are odd OR both are even
        // odd + odd and odd - odd will always be an even number
        // even + even and even - even will always be an even number
        // if the midpoint of the 2 numbers is and odd number, not possible to reach
        if(((x+y)/2)%2==1){
            return true;
        }

        //if x+y is a multiple of a power of 2, will end up in a draw;
        int hcf=hcf(x,y);
        int units=(x+y)/hcf;
        while (units%2==0 && units!=1){
            units/=2;
        }
        return units!=1;
    }

    //find the highest common factor
    public static int hcf(int x, int y){
        int smaller,larger;
        if (x > y) {
            smaller=y;
            larger=x;
        }else{
            smaller=x;
            larger=y;
        }
        while(smaller!=0){
            int temp=smaller;
            smaller=larger%smaller;
            larger=temp;
        }
        return larger;
    }
}