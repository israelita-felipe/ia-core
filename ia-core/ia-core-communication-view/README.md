# ia-core-communication-view

## 📋 Descrição

Interface web com Vaadin para gerenciamento de comunicações. Monitora emails, SMS e notificações enviadas.

## 🏗️ Estrutura

```
ia-core-communication-view/
├── src/main/java/
│   └── com/ia/core/communication/view/
│       ├── email/                 # Views de email
│       ├── sms/                   # Views de SMS
│       ├── notification/          # Views de notificação
│       └── component/             # Componentes
└── pom.xml
```

## 🔑 Responsabilidades

- **EmailHistoryView**: Histórico de emails enviados
- **EmailTemplateManagerView**: CRUD de templates
- **NotificationDashboard**: Dashboard de notificações
- **CommunicationMetrics**: Estatísticas

## 🛠️ Tecnologias

- Vaadin
- Spring Boot

## 💡 Componentes

- `EmailGrid`: Lista de emails com status
- `TemplateEditor`: Editor de templates HTML
- `DeliveryStatus`: Indicador de status de entrega


