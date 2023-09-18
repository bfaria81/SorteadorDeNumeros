package com.sorteio.simulator.Service;

import com.sorteio.simulator.Model.M_Sorteio;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class S_Sorteio {

    public static M_Sorteio sorteio(int numeros, int numInicio, int numFim, boolean permitirRepeticao) {
        int[] sorteio = new int[numeros];
        Set<Integer> numerosSorteados = new HashSet<>();

        // Verifique se é possível gerar números únicos suficientes
        if (!permitirRepeticao && (numFim - numInicio + 1) < numeros) {
            throw new IllegalArgumentException("Não é possível gerar números únicos suficientes no intervalo especificado.");
        }

        for (int i = 0; i < numeros; i++) {
            int resultado;

            if (permitirRepeticao) {
                // Se permitir repetição, basta gerar um número aleatório em todo o intervalo
                resultado = new Random().nextInt(numFim - numInicio + 1) + numInicio;
            } else {
                // Se não permitir repetição, continue gerando números até obter um único
                do {
                    resultado = new Random().nextInt(numFim - numInicio + 1) + numInicio;
                } while (numerosSorteados.contains(resultado));
            }

            sorteio[i] = resultado;
            numerosSorteados.add(resultado);
        }

        M_Sorteio m_sorteio = new M_Sorteio(sorteio);
        return m_sorteio;
    }

}