package world.ucode.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "messages")
public class Mes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Registration sender;
    private String receiver;
//    @NotBlank(message = "Заполните поле текст сообщения")
//    @Length(max = 2048, message = "Слишком длинное сообщение, превышает 2Kb")
    private String mes;
    private String data;

    public Mes() {}

    public Mes(Registration sender, String receiver, String mes, String data) {
        this.sender = sender;
        this.receiver = receiver;
        this.mes = mes;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public Registration getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMes() {
        return mes;
    }

    public void setSender(Registration sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
