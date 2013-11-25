/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.dmbob;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Bobby
 */
public class GridTile {
    private int x, y, width, height;
    private Being person;
    
    public GridTile(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, 32, 32);
        g.setColor(Color.black);
        g.drawRect(x - 1, y - 1, 32 + 2, 32 + 2);
        if(person != null) {
            person.draw(g);
        }
    }
    
    public void setBeing(Being b) {
        person = b;
    }
    
    public Being getBeing() {
        if(person != null) {
            return person;
        }
        return null;
    }
}
