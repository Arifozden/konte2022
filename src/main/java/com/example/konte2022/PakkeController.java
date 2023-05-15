package com.example.konte2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
public class PakkeController {
    @Autowired
    private JdbcTemplate db;

    Logger logger= LoggerFactory.getLogger(PakkeController.class);

    @PostMapping("/lagrePakke")
    public boolean lagrePakke(Pakke pakke, HttpServletResponse response) throws IOException {
        String sjekkLID="SELECT LID FROM Lager WHERE LID=?";
        String sqlPakke="INSERT INTO Pakke (LID,Eier,Vekt,Volum) VALUES (?,?,?,?)";
        String regex= "[a-zæøåA-ZÆØÅ .\\-]{2,50}";
        boolean eierOK=pakke.getEier().matches(regex);
        boolean vektOK=false;
        if(pakke.getVekt()>0){
            vektOK=true;
        }
        boolean volumOK=false;
        if(pakke.getVolum()>0){
            volumOK=true;
        }
        if(eierOK&&vektOK&&volumOK){
            try {
                int LID=db.queryForObject(sjekkLID, Integer.class,pakke.getLID());
                db.update(sqlPakke,LID,pakke.getEier(),pakke.getVekt(),pakke.getVolum());
            }catch (Exception e){
                logger.error("Feil ved innlegging av ny pakke:"+e);
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Det skjedde en feil ved innlegging av ny pakke i databasen. Prøv igjen om litt.");
            }
        }else {logger.error("Feil ved innlegging av ny pakke pga. inputvalidering.");
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Det skjedde en feil ved innlegging av ny pakke. En eller flere felter er feil utfylt.");
    }
        return true;
    }

    @Autowired
    HttpSession session;
    @PostMapping("/logginn")
    public boolean loggInn(Bruker bruker){
        String sql="SELECT * FROM Bruker WHERE Navn=?";
        Bruker dbBruker;
        try {
            dbBruker=db.queryForObject(sql, BeanPropertyRowMapper.newInstance(Bruker.class),bruker.getNavn());
        }catch (Exception e){
            return false;
        }
        String hashetPassord= dbBruker.getPassord();
        if(BCrypt.checkpw(bruker.getPassord(),hashetPassord)){
            session.setAttribute("innlogget",bruker.getNavn());
            return true;
        }
        return false;
    }

    @GetMapping("/hentAllePakker")
    public List<Pakke>hentAllePakker(HttpServletResponse response) throws IOException{
        String sql="SELECT * FROM Pakke";
        List<Pakke> allePakker=db.query(sql,new BeanPropertyRowMapper(Pakke.class));
        if(session.getAttribute("innlogget")==null){
            response.sendError(HttpStatus.FORBIDDEN.value(),"Du må først logge inn for å kunne liste alle pakker.");
        }
        return allePakker;
    }

    @GetMapping("/stat")
    public String stat(){
        String sqlLager="SELECT * FROM Lager";
        List<Lager>alleLagere=db.query(sqlLager,new BeanPropertyRowMapper(Lager.class));
        String oppsummering="";
        for(Lager lager:alleLagere){
            String sqlPakke="SELECT * FROM Pakke WHERE LID=?";
            List<Pakke>allePakker=db.query(sqlPakke,new BeanPropertyRowMapper(Pakke.class),lager.getLID());

            int antallPakker=0;
            double vekt=0.0;
            double volum=0.0;
            for (Pakke pakke:allePakker){
                ++antallPakker;
                vekt+=pakke.getVekt();
                volum+=pakke.getVolum();
            }
            oppsummering+=lager.getNavn()+"innholder"+antallPakker+"pakker med et total volum på"+volum+
                    "kubikkmeter og en totalvekt på "+vekt+" kg.";
        }
        return oppsummering;
    }
}

