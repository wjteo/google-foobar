package level2.challenge2;

import java.util.Arrays;

public class Solution {

    public static String[] solution(String[] l){
        Arrays.sort(l,(a,b)->{
            String[] aVersions = a.split("\\.");
            String[] bVersions = b.split("\\.");
            //Compare major versions
            int aMajor = Integer.parseInt(aVersions[0]);
            int bMajor = Integer.parseInt(bVersions[0]);
            if(aMajor>bMajor){
                return 1;
            }
            if(aMajor<bMajor){
                return -1;
            }

            //Compare minor versions
            int aMinor,bMinor;
            if(aVersions.length<2){
                aMinor=-1;
            }else{
                aMinor = Integer.parseInt(aVersions[1]);
            };
            if(bVersions.length<2){
                bMinor=-1;
            }else{
                bMinor=Integer.parseInt(bVersions[1]);
            }
            if(aMinor>bMinor){
                return 1;
            }
            if(aMinor<bMinor){
                return -1;
            }
            if(aMinor==-1){
                return 0;
            }

            //Compare revisions
            int aRevision,bRevision;
            if(aVersions.length<3){
                aRevision=-1;
            }else{
                aRevision = Integer.parseInt(aVersions[2]);
            };
            if(bVersions.length<3){
                bRevision=-1;
            }else{
                bRevision=Integer.parseInt(bVersions[2]);
            }
            if(aRevision>bRevision){
                return 1;
            }
            if(aRevision<bRevision){
                return -1;
            }
            return 0;
        });
        return l;
    }
}
