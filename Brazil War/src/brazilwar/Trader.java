/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brazilwar;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * classe responsavel pelas trocas e suas regras
 * @author darts
 */
public class Trader {
    private static Trader instance = null;
    private int count;

    private Trader() {
        this.count = 0;
    }
        
    public static Trader getInstance(){
        if(instance == null){
            instance = new Trader();
        }
        return instance;
    }
        
    /**
     * realiza a troca das cartas por tropas
     * @param cards colecao de cartas
     * @return quantidade de tropas a serem recebidas
     */
    public ArrayList<BattleUnit> getTrade(ArrayList<Card> cards){
        int qtt = 0;
        if (isValidTrade(cards)){
            qtt = Parameters.FIRST_TRADE + Parameters.TRADE_ACCUMULATOR*count;
            count++;            
        }
        ArrayList<BattleUnit> units = new ArrayList<>();
        for (int i = 0; i < qtt; i++) {
            units.add(new BattleUnit());
        }
        return units;
    }
    
    /**
     * checa se as cartas recebidas são uma troca valida
     * @param cards
     * @return
     */
    public boolean isValidTrade(ArrayList<Card> cards){
        return canTradeCards(cards) && cards.size() == Parameters.SYMBOLS.length;
    }
    
    //checa se existe alguma combinação de troca nas cartas recebidas
    public boolean canTradeCards(ArrayList<Card> cards){
        //será contado as ocorrencias de cada simbolo
        int occurrences[] = new int[Parameters.SYMBOLS.length]; 
        
        //impossivel ter uma combinação
        if (cards.size() < Parameters.SYMBOLS.length){
            return false;
        }
        else if(hasJoker(cards)){//tem 3 ou mais cartas e um coringa entre elas
            return true;
        }
        //impossivel não ter uma combinação
        else if(cards.size() > (Parameters.SYMBOLS.length-1)*Parameters.SYMBOLS.length-1){
            return true;
        }
        
        for (int i = 0; i < Parameters.SYMBOLS.length; i++) {
            for (int j = 0; j < cards.size(); j++) {
                if(Parameters.SYMBOLS[i].equals(cards.get(j).getSymbol())){
                    occurrences[i]++;
                }
            }
        }
        Arrays.sort(occurrences);
        if ((occurrences[0] < Parameters.SYMBOLS.length) || (occurrences[occurrences.length-1] == 0)){
            return false;
        }
        return true;
    }
    
    /**
     * checa se existe um coringa entre as cartas
     * @param cards
     * @return
     */
    private boolean hasJoker(ArrayList<Card> cards){
        for(Card card : cards){
            if (card.getSymbol().equals(Parameters.JOKER_SYMBOL)){
                return true;
            }
        }
        return false;
    }
}
