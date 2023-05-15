function validerLid(lid){
    if(lid){
        $("#feilLid").html("");
        return true;
    }else {
        $("#feilLid").html("Fyll ut feltet");
        return false;
    }
}

function validerEier(eier){
    let regex=/^[A-ZÆØÅa-zæøå.-]{2,50}$/;
    if(regex.test(eier)){
        $("#feilEier").html("");
        return true;
    }else {
        $("#feilEier").html("Bruk kun store og små bokstaver, og ., - og mellomrom");
        return false;
    }
}

function validerVekt(vekt){
    if(vekt){
        $("#feilVekt").html("");
        return true;
    }else {
        $("#feilVekt").html("Fyll ut feltet");
        return false;
    }
}

function validerVolum(volum){
    if(volum){
        $("#feilVolum").html("");
        return true;
    }else {
        $("#feilVolum").html("Fyll ut feltet");
        return false;
    }
}

function validerPakke(pakke){
    lidOK=validerLid(pakke.LID);
    eierOK=validerEier(pakke.eier);
    vektOK=validerVekt(pakke.vekt);
    volumOK=validerVolum(pakke.volum);
    if(lidOK&&eierOK&&vektOK&&volumOK){
        return true;
    }else {
        return false;
    }
}

function registrerPakke(){
    let pakke={
        "LID":$("#lid").val(),
        "eier":$("#eier").val(),
        "vekt":$("#vekt").val(),
        "volum":$("#volum").val()
    };
    if(validerPakke(pakke)){
        $.post("lagrePakke",pakke,function (){
            $("#melding").html("Pakke ble lagret");
            $("#lid").val("");
            $("#eier").val("");
            $("#vekt").val("");
            $("#volum").val("");
        });
    }else {
        $("#melding").html("Fyll ut alle felter og rett alle feil i skjemaet før innsending");
    }
}
function hentAllePakker(){
    $.get("hentAllePakker",function (pakker){
        let ut="<table><tr><th>LID</th><th>Eier</th><th>Vekt(kg)</th><th>Volum(m^3)</th></tr>";
        for(let pakke of pakker){
            ut+="<tr><td>"+pakke.lid+"</td><td>"+pakke.eier+"</td><td>"+pakke.vekt+"</td><td>"+pakke.volum+"</td><tr>";
        }
        ut+="</table>";
        $("#pakkeliste").html(ut);
    });
}