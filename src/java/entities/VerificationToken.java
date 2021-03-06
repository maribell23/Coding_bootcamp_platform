/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "verification_token")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VerificationToken.findAll", query = "SELECT v FROM VerificationToken v")
    , @NamedQuery(name = "VerificationToken.findById", query = "SELECT v FROM VerificationToken v WHERE v.id = :id")
    , @NamedQuery(name = "VerificationToken.findByToken", query = "SELECT v FROM VerificationToken v WHERE v.token = :token")
    , @NamedQuery(name = "VerificationToken.findByExpiryDate", query = "SELECT v FROM VerificationToken v WHERE v.expiryDate = :expiryDate")})
public class VerificationToken implements Serializable {

    private static final int EXPIRATION = 60 * 24;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "token")
    private String token;
    @Column(name = "expiry_date")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @OneToOne
    private Student studentId;

    public VerificationToken() {
        this.expiryDate = calculateExpiryDate(EXPIRATION);
        this.token = UUID.randomUUID().toString();
    }

    public VerificationToken(String token, Student studentId) {
        this.token = token;
        this.studentId = studentId;

    }

    public VerificationToken(Student studentId) {

        this.studentId = studentId;
        System.out.println(studentId);
        String t = UUID.randomUUID().toString();
        this.token = t;
        this.expiryDate = calculateExpiryDate(EXPIRATION);

    }

    public VerificationToken(Integer id) {
        this.id = id;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
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
        if (!(object instanceof VerificationToken)) {
            return false;
        }
        VerificationToken other = (VerificationToken) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.VerificationToken[ id=" + id + " ]";
    }
}
