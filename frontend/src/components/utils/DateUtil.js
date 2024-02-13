export default {
    formatDate(input) {
        const data = new Date(input);

        // Extrair as partes da data
        const ano = data.getFullYear();
        const dia = data.getDate();
        const mes = data.getMonth() + 1; // Lembrando que o mês é base 0, então adicionamos 1
        const horas = data.getHours();
        const minutos = data.getMinutes();

        // Adicionar zeros à esquerda, se necessário
        const diaFormatado = dia < 10 ? '0' + dia : dia;
        const mesFormatado = mes < 10 ? '0' + mes : mes;
        const horasFormatadas = horas < 10 ? '0' + horas : horas;
        const minutosFormatados = minutos < 10 ? '0' + minutos : minutos;

        // Retornar a data formatada
        return `${ano}-${diaFormatado}-${mesFormatado} ${horasFormatadas}:${minutosFormatados}`;
    }
}