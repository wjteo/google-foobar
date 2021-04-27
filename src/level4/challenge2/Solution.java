package level4.challenge2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class Solution {
    public static int solution(int[] dimensions, int[] your_position, int[] trainer_position, int distance) {
        Room room=new Room(0,0,dimensions[0],dimensions[1]);
        Position shooter=new Position(your_position[0],your_position[1]);
        Position trainer=new Position(trainer_position[0],trainer_position[1]);

        Image realImage=new Image(room,shooter,trainer);
        Map<Double,Target> reachableTargets = loadTargetsFromShooter(realImage,shooter,trainer,distance);
        int count=0;

        Set<Double> angles = reachableTargets.keySet();
        for(double angle: angles){
            Target target = reachableTargets.get(angle);
            if(target.type== Target.Type.TRAINER){
                count++;
            }
        }
        return count;
    }

    //generate a set of reflections as if the 4 walls were mirrors.
    //a target is an image of shooter or trainer which a beam can potentially hit
    //load all targets by computing the position of targets relative to the shooter in terms of angle and distance
    //if more than 1 target shares the same angle from the shooter, only the nearest target will be hit
    public static Map<Double,Target> loadTargetsFromShooter(Image image,Position realShooter , Position realTrainer, double range){
        ReachableTargetsStore store=new ReachableTargetsStore();

        Target realTrainerTarget=Target.computeTarget(Target.Type.TRAINER,realShooter,realTrainer);
        if(!realTrainerTarget.isWithinRange(range)){
            return store.getTargetsMap();
        }

        store.loadTarget(realTrainerTarget);

        List<Image> verticalReflections= new ArrayList<>();
        verticalReflections.add(image);

        //reflect upwards until targets are no longer in range
        boolean targetsInRange=true;
        Image nextImage=image;

        while(targetsInRange){
            Image reflected=nextImage.reflectUp();

            Target shooterImage=Target.computeTarget(Target.Type.SHOOTER,realShooter,reflected.shooter);
            if(shooterImage.isWithinRange(range)){
                store.loadTarget(shooterImage);
            }

            Target trainerImage=Target.computeTarget(Target.Type.TRAINER,realShooter,reflected.trainer);
            if(trainerImage.isWithinRange(range)){
                store.loadTarget(trainerImage);
            }
            targetsInRange=shooterImage.isWithinRange(range)||trainerImage.isWithinRange(range);
            if(targetsInRange){
                verticalReflections.add(reflected);
            }
            nextImage=reflected;
        }

        //reflect downwards until targets are no longer in range
        targetsInRange=true;
        nextImage=image;

        while(targetsInRange){
            Image reflected=nextImage.reflectDown();

            Target shooterImage=Target.computeTarget(Target.Type.SHOOTER,realShooter,reflected.shooter);
            if(shooterImage.isWithinRange(range)){
                store.loadTarget(shooterImage);
            }

            Target trainerImage=Target.computeTarget(Target.Type.TRAINER,realShooter,reflected.trainer);
            if(trainerImage.isWithinRange(range)){
                store.loadTarget(trainerImage);
            }
            targetsInRange=shooterImage.isWithinRange(range)||trainerImage.isWithinRange(range);
            if(targetsInRange){
                verticalReflections.add(reflected);
            }
            nextImage=reflected;
        }
        for(Image verticalReflection: verticalReflections){
            targetsInRange=true;
            nextImage=verticalReflection;

            while(targetsInRange){
                Image reflected=nextImage.reflectLeft();
                Target shooterImage=Target.computeTarget(Target.Type.SHOOTER,realShooter,reflected.shooter);
                if(shooterImage.isWithinRange(range)){
                    store.loadTarget(shooterImage);
                }

                Target trainerImage=Target.computeTarget(Target.Type.TRAINER,realShooter,reflected.trainer);
                if(trainerImage.isWithinRange(range)){
                    store.loadTarget(trainerImage);
                }
                targetsInRange=shooterImage.isWithinRange(range)||trainerImage.isWithinRange(range);
                nextImage=reflected;
            }
        }
        for(Image verticalReflection: verticalReflections){
            targetsInRange=true;
            nextImage=verticalReflection;

            while(targetsInRange){
                Image reflected=nextImage.reflectRight();
                Target shooterImage=Target.computeTarget(Target.Type.SHOOTER,realShooter,reflected.shooter);
                if(shooterImage.isWithinRange(range)){
                    store.loadTarget(shooterImage);
                }

                Target trainerImage=Target.computeTarget(Target.Type.TRAINER,realShooter,reflected.trainer);
                if(trainerImage.isWithinRange(range)){
                    store.loadTarget(trainerImage);
                }
                targetsInRange=shooterImage.isWithinRange(range)||trainerImage.isWithinRange(range);
                nextImage=reflected;
            }
        }
        return store.getTargetsMap();
    }

    public static class ReachableTargetsStore{
        Map<Double,Target> targetsMap;
        public ReachableTargetsStore(){
            this.targetsMap=new HashMap<>();
        }

        public void loadTarget(Target target){ //saves and replaces a target that is further away in the same angle
            if(targetsMap.containsKey(target.angle)){
                Target current=targetsMap.get(target.angle);
                if(target.distance>current.distance){
                    return;
                }
            }
            targetsMap.put(target.angle,target);
        }

        public Map<Double,Target> getTargetsMap(){
            return targetsMap;
        }
    }

    public static class Target{
        public enum Type{
            SHOOTER,TRAINER
        }
        public Type type;
        public double distance;
        public double angle;

        public Target(Type type,double distance, double angle){
            this.type=type;
            this.distance=distance;
            this.angle=angle;
        }

        public static Target computeTarget(Type type, Position shooterPos, Position targetPos){
            double relativeX=targetPos.x-shooterPos.x;
            double relativeY=targetPos.y-shooterPos.y;
            double distance= Math.sqrt((relativeX*relativeX)+(relativeY*relativeY));
            double angle=Math.atan2(relativeY,relativeX);
            return new Target(type,distance,roundAngle(angle));
        }

        private static double roundAngle(double angle){ //roundUp angle so that we can use this as a key in a hashSet
            BigDecimal bd = new BigDecimal(Double.toString(angle));
            bd = bd.setScale(9, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }

        public boolean isWithinRange(double range){
            return distance<=range;
        }
    }

    public static class Position{
        public int x;
        public int y;
        public Position(int x, int y){
            this.x=x;
            this.y=y;
        }
    }

    public static class Room{
        //x and y indicates position of bottom left corner of room;
        public int x;
        public int y;
        public int width;
        public int height;
        public Room(int x, int y, int width, int height){
            this.x=x;
            this.y=y;
            this.width=width;
            this.height=height;
        }
    }

    public static class Image{
        public Room room; //x,y,width,height
        public Position shooter;
        public Position trainer;


        public Image(Room room, Position shooter, Position trainer){
            this.room=room;
            this.shooter=shooter;
            this.trainer=trainer;
        }

        public Image reflectUp(){
            Room reflectedRoom=new Room(room.x,room.y+room.height,room.width,room.height);
            Position reflectedShooter=new Position(shooter.x,shooter.y+2*(reflectedRoom.y-shooter.y));
            Position reflectedTrainer=new Position(trainer.x,trainer.y+2*(reflectedRoom.y-trainer.y));
            return new Image(reflectedRoom,reflectedShooter,reflectedTrainer);
        }

        public Image reflectDown(){
            Room reflectedRoom=new Room(room.x,room.y-room.height,room.width,room.height);
            Position reflectedShooter=new Position(shooter.x,shooter.y-2*(shooter.y-room.y));
            Position reflectedTrainer=new Position(trainer.x,trainer.y-2*(trainer.y-room.y));
            return new Image(reflectedRoom,reflectedShooter,reflectedTrainer);
        }

        public Image reflectLeft(){
            Room reflectedRoom=new Room(room.x-room.width,room.y,room.width,room.height);
            Position reflectedShooter=new Position(shooter.x-2*(shooter.x-room.x),shooter.y);
            Position reflectedTrainer=new Position(trainer.x-2*(trainer.x-room.x),trainer.y);
            return new Image(reflectedRoom,reflectedShooter,reflectedTrainer);
        }

        public Image reflectRight(){
            Room reflectedRoom=new Room(room.x+room.width,room.y,room.width,room.height);
            Position reflectedShooter=new Position(shooter.x+2*(reflectedRoom.x-shooter.x),shooter.y);
            Position reflectedTrainer=new Position(trainer.x+2*(reflectedRoom.x-trainer.x),trainer.y);
            return new Image(reflectedRoom,reflectedShooter,reflectedTrainer);
        }

    }







}