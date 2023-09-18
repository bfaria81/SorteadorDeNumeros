package com.sorteio.simulator.Controller;

import com.sorteio.simulator.Model.M_Sorteio;
import com.sorteio.simulator.Service.S_Sorteio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

@Controller
public class C_Sorteio {
    @GetMapping("/")
    public String getSorteio() {
        return "index";
    }


    public class SorteioException extends RuntimeException {
        public SorteioException(String message) {
            super(message);
        }
    }

    @PostMapping("/")
    public String postSorteio(@RequestParam("qtd_numeros") int qtd_numeros,
                            @RequestParam("numeroInicio") int numeroInicio,
                            @RequestParam("numeroFim") int numeroFim,
                            @RequestParam(value = "ordenarCheckbox", required = false, defaultValue = "false") boolean ordenarCheckbox,
                            @RequestParam(value = "repetirCheckbox", required = false, defaultValue = "false") boolean repetirCheckbox,
                            Model model) {




        boolean permitirRepeticao = repetirCheckbox;

        if (!repetirCheckbox && (numeroFim - numeroInicio + 1) < qtd_numeros) {
            // Se não permitir repetição e não há números únicos suficientes
            model.addAttribute("mensagemErro", "Não é possível gerar números únicos suficientes no intervalo especificado.");
            return "index";
        }

        try {
            M_Sorteio sorteio = S_Sorteio.sorteio(qtd_numeros, numeroInicio, numeroFim, permitirRepeticao);

            int[] numerosSorteados = sorteio.getSorteio();

            if (!repetirCheckbox) {
                // Se não permitir repetição, remova os números duplicados
                numerosSorteados = Arrays.stream(numerosSorteados).distinct().toArray();
            }

            if (ordenarCheckbox) {
                // Se ordenar em ordem crescente estiver selecionado, ordene os números
                Arrays.sort(numerosSorteados);
            }

            Date dataSorteio = new Date();
            TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT-3");
            TimeZone.setDefault(gmtTimeZone);

            model.addAttribute("sorteio", numerosSorteados);
            model.addAttribute("dataSorteio", dataSorteio);
        }catch (SorteioException e){
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
        }
        return "index";
    }
}
