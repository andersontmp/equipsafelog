export default {
    formatDate(input) {
        const data = new Date(input);

        // Extrair as partes da data
        const ano = data.getFullYear();
        const dia = data.getDate();
        const mes = data.getMonth() + 1;

        // Adicionar zeros à esquerda, se necessário
        const diaFormatado = dia < 10 ? '0' + dia : dia;
        const mesFormatado = mes < 10 ? '0' + mes : mes;

        // Retornar a data formatada
        return `${diaFormatado}/${mesFormatado}/${ano}`;
    },
    formatDateWithWeek(input) {
        const data = new Date(input);

        // Extrair as partes da data
        const ano = data.getFullYear();
        const dia = data.getDate();
        const mes = data.getMonth() + 1;
        const week = data.getDay();

        // Adicionar zeros à esquerda, se necessário
        const diaFormatado = dia < 10 ? '0' + dia : dia;
        const mesFormatado = mes < 10 ? '0' + mes : mes;

        var diaDaSemanaString;
        switch (week) {
            case 0:
                diaDaSemanaString = "Domingo";
                break;
            case 1:
                diaDaSemanaString = "Segunda-feira";
                break;
            case 2:
                diaDaSemanaString = "Terça-feira";
                break;
            case 3:
                diaDaSemanaString = "Quarta-feira";
                break;
            case 4:
                diaDaSemanaString = "Quinta-feira";
                break;
            case 5:
                diaDaSemanaString = "Sexta-feira";
                break;
            case 6:
                diaDaSemanaString = "Sábado";
                break;
            default:
                diaDaSemanaString = "Erro ao obter dia da semana";
        }

        // Retornar a data formatada
        return `${diaFormatado}/${mesFormatado}/${ano} - ${diaDaSemanaString}`;
    },
    formatDateTime(input) {
        if(input){
            const data = new Date(input);
    
            // Extrair as partes da data
            const ano = data.getFullYear();
            const dia = data.getDate();
            const mes = data.getMonth() + 1; // Lembrando que o mês é base 0, então adicionamos 1
            const horas = data.getHours();
            const minutos = data.getMinutes();
            const segundos = data.getSeconds();
    
            // Adicionar zeros à esquerda, se necessário
            const diaFormatado = dia < 10 ? '0' + dia : dia;
            const mesFormatado = mes < 10 ? '0' + mes : mes;
            const horasFormatadas = horas < 10 ? '0' + horas : horas;
            const minutosFormatados = minutos < 10 ? '0' + minutos : minutos;
            const segundosFormatados = segundos < 10 ? '0' + segundos : segundos;
    
            // Retornar a data formatada
            return `${diaFormatado}/${mesFormatado}/${ano} ${horasFormatadas}:${minutosFormatados}:${segundosFormatados}`;
        }
        return '';
    }
}