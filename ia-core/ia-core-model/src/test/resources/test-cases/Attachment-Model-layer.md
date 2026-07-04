# Caso de Teste: Attachment

## Descrição
Testa a classe Attachment que representa anexos de arquivos no sistema.

## Classe Testada
`com.ia.core.model.attachment.Attachment`

## Fluxo do Teste
1. Criar Attachment com dados básicos
2. Testar getters e setters
3. Testar validação de campos obrigatórios
4. Testar comportamento com JPA

## Cenários

### Cenário 1: Criar Attachment com dados básicos
- **Dado**: Nome de arquivo, tipo MIME e tamanho
- **Quando**: Criar Attachment
- **Então**: Deve armazenar nome do arquivo
- **E**: Deve armazenar tipo MIME
- **E**: Deve armazenar tamanho

### Cenário 2: Testar getter de nome do arquivo
- **Dado**: Um Attachment criado com nome "documento.pdf"
- **Quando**: Chamar getFileName()
- **Então**: Deve retornar "documento.pdf"

### Cenário 3: Testar getter de tipo MIME
- **Dado**: Um Attachment criado com tipo "application/pdf"
- **Quando**: Chamar getMimeType()
- **Então**: Deve retornar "application/pdf"

### Cenário 4: Testar getter de tamanho
- **Dado**: Um Attachment criado com tamanho 1024
- **Quando**: Chamar getSize()
- **Então**: Deve retornar 1024

### Cenário 5: Testar setter de nome do arquivo
- **Dado**: Um Attachment criado
- **Quando**: Chamar setFileName("novo.pdf")
- **Então**: Deve atualizar o nome do arquivo

### Cenário 6: Testar setter de tipo MIME
- **Dado**: Um Attachment criado
- **Quando**: Chamar setMimeType("image/jpeg")
- **Então**: Deve atualizar o tipo MIME

### Cenário 7: Testar setter de tamanho
- **Dado**: Um Attachment criado
- **Quando**: Chamar setSize(2048)
- **Então**: Deve atualizar o tamanho
