grammar Leitura;

/////////////////////////////////////////////////////////////////
// LÉXICO
/////////////////////////////////////////////////////////////////

NUMERO: [0-9]+;
TEXTO: [a-zA-Z ]+;
E: '.';
NOVO_CAP_TOKEN: [;];
INTERVAL_TOKEN: [-];
COMMA_TOKEN: [,:];
WS: [\r\n\t]+ -> skip;

/////////////////////////////////////////////////////////////////
// PRODUÇÃO
/////////////////////////////////////////////////////////////////
program: 
	leituras EOF;

livro: 
    NUMERO TEXTO | 
    TEXTO;
    
leituras:
    leitura |
	leitura NOVO_CAP_TOKEN leituras|
	leitura NOVO_CAP_TOKEN capitulo COMMA_TOKEN versiculos |
	leitura NOVO_CAP_TOKEN capitulo COMMA_TOKEN versiculos NOVO_CAP_TOKEN leituras;	    
        
leitura: 
    livro capitulo COMMA_TOKEN versiculos;		        
        
capitulo:   
	NUMERO;

versiculos: 
    versiculo |
    intervaloVersiculo |
    composicaoVersiculos;
    
intervaloVersiculo:
	versiculo INTERVAL_TOKEN versiculo;
	
composicaoVersiculos:
	versiculo E versiculos |
    intervaloVersiculo E versiculos;		    

versiculo: NUMERO;

