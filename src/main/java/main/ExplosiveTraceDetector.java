package main;

import main.configuration.Configuration;

public class ExplosiveTraceDetector {

    public void checkTestStripeForExplosive(TestStripe testStripe){
        String content = "";
        for(int i = 0; i < testStripe.getContent().length; i++){
            for(int j = 0; j < testStripe.getContent()[i].length; j++){
                content += testStripe.getContent()[i][j];
            }
        }

        String position = Configuration.instance.bruteForce.search(content, "exp");

        if(position.equals("clean")){
            System.out.println("No explosive detected, HandBaggage clean");
        }else{
            System.out.println("explosive detected, fund confirmed");
        }
    }
}
