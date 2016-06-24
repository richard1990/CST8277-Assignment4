/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Richard
 */
@Entity
@Table(name = "sprite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sprite.findAll", query = "SELECT s FROM Sprite s"),
    @NamedQuery(name = "Sprite.findById", query = "SELECT s FROM Sprite s WHERE s.id = :id"),
    @NamedQuery(name = "Sprite.findByDx", query = "SELECT s FROM Sprite s WHERE s.dx = :dx"),
    @NamedQuery(name = "Sprite.findByDy", query = "SELECT s FROM Sprite s WHERE s.dy = :dy"),
    @NamedQuery(name = "Sprite.findByPanelheight", query = "SELECT s FROM Sprite s WHERE s.panelheight = :panelheight"),
    @NamedQuery(name = "Sprite.findByPanelwidth", query = "SELECT s FROM Sprite s WHERE s.panelwidth = :panelwidth"),
    @NamedQuery(name = "Sprite.findByX", query = "SELECT s FROM Sprite s WHERE s.x = :x"),
    @NamedQuery(name = "Sprite.findByY", query = "SELECT s FROM Sprite s WHERE s.y = :y")})
public class Sprite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Lob
    @Column(name = "COLOR")
    private byte[] color;
    @Column(name = "DX")
    private Integer dx;
    @Column(name = "DY")
    private Integer dy;
    @Column(name = "PANELHEIGHT")
    private Integer panelheight;
    @Column(name = "PANELWIDTH")
    private Integer panelwidth;
    @Column(name = "X")
    private Integer x;
    @Column(name = "Y")
    private Integer y;

    public Sprite() {
    }

    public Sprite(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getColor() {
        return color;
    }

    public void setColor(byte[] color) {
        this.color = color;
    }

    public Integer getDx() {
        return dx;
    }

    public void setDx(Integer dx) {
        this.dx = dx;
    }

    public Integer getDy() {
        return dy;
    }

    public void setDy(Integer dy) {
        this.dy = dy;
    }

    public Integer getPanelheight() {
        return panelheight;
    }

    public void setPanelheight(Integer panelheight) {
        this.panelheight = panelheight;
    }

    public Integer getPanelwidth() {
        return panelwidth;
    }

    public void setPanelwidth(Integer panelwidth) {
        this.panelwidth = panelwidth;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sprite)) {
            return false;
        }
        Sprite other = (Sprite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Sprite[ id=" + id + " ]";
    }
    
}
