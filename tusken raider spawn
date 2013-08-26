package tuskenraiderspwn;

import java.util.Scanner;

+import java.util.Random;


public class TuskenRaiderSpwn {
  private int Health = 100
	private int action = 200
	private CDF cdf;
    private int anim; 
    private boolean walking = false;
    private Random random = new random();
    public int rand; 
    public long timer = System.currentTimeMillis();
    
    public int location;
    Scanner mem = new Scanner (System.in);
    private final int x;
    private final int y;
    
    
    public TuskenRaiderSpwn (int x, int y){
        this.x = 4160;
        this.y = -4780;
        cdf = client_shared_npc_dressed_commoner_old_human_male_01.cdf;
    }
    
    public void teleport (int x,int y);{
    this.x = 4160;
    this.y = 4780;
} 
  
    public void update(); {
    int xa = 0, int ya = 0
      if (anim < 7500)
          anim++;
      else 
          anim = 0; 
      if (System.currentTimeMillis()- timer > 500){
          timer += 500;
          if (!(level.getTile((x + 30) /16, (y + 30) / 16 )).solid()) {
              rand = random.nextInt(20);
              //System.out.println("Next tile is not solid!");
              
          } else {
              //System.out.println("Next tile is solid, moving in dir:" + dir);
              if (dir == 0) {
                  rand = 1;
              }
              if (dir == 1) {
                  rand = 2;
              }
              if (dir == 2) {
                  rand = 0;
              }
              if (dir == 3) {
                  rand = 3; 
              }
          }
      }
      //System.out.println("Anim: " + anim + ", Rand: " + rand);
      if (rand == 0) ya--;
      if (rand == 1) ya++;
      if (rand == 2) xa--;
      if (rand == 3) xa++;
      
      
      if (xa != 0 || ya != 0){
          move (xa,ya); 
          walking = true;
      } else {
          walking = false;
      }
}

    public void render (Screen screen) {
        int flip = 0 
        if (dir ==0){
        	cdf = client_shared_npc_dressed_commoner_old_human_male_01.cdf;
        	if (walking) {
        		if (anim % 20 > 10) {
        			cdf = client_shared_npc_dressed_commoner_old_human_male_01.cdf;
        			
        		} else {
        			cdf = client_shared_npc_dressed_commoner_old_human_male_01.cdf;
        		}
        	}
        } if (dir == 1 || dir == 3) { 
        	cdf = client_shared_npc_dressed_commoner_old_human_male_01.cdf;
        	
        	
       	
        	
        	
        	
        	
        	
        	
        	
        	
        }
    }
    
    
    
    
