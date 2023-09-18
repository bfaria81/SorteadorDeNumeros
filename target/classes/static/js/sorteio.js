$(document).ready(function () {
    $("#btnSortear").click(function () {
        // Obtém os valores dos campos do formulário
        let qtdNumeros = $("#qtd_numeros").val();
        let numeroInicio = $("#numeroInicio").val();
        let numeroFim = $("#numeroFim").val();
        let ordenarCheckbox = $("#ordenarCheckbox").is(":checked");
        let repetirCheckbox = $("#repetirCheckbox").is(":checked");


        // Crie um objeto com os dados a serem enviados para o servidor
        let dados = {
            qtd_numeros: qtdNumeros,
            numeroInicio: numeroInicio,
            numeroFim: numeroFim,
            ordenarCheckbox: ordenarCheckbox,
            repetirCheckbox: repetirCheckbox
        };

        $.ajax({
            type: "POST",
            url: "/",
            data: dados,
            success: function (response) {
                // A resposta do servidor contém os resultados do sorteio
                // Atualize a seção de resultados com os dados recebidos
                document.open();
                document.write(response);
                document.close();
                atualizarDataHora();

            }
        });
    });
});


function atualizarDataHora() {
    let dataAtual = new Date();
    let dataFormatada = dataAtual.toLocaleDateString();
    let horaFormatada = dataAtual.toLocaleTimeString();
    $("#dataHora").text(new Date().toLocaleDateString() + ' ' + new Date().toLocaleTimeString());
};


