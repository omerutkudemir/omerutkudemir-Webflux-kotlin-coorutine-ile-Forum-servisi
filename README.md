---

## Proje Hakkında

Uygulama, kullanıcıların gönderi paylaşabildiği, yanıtlayabildiği ve beğenilerle etkileşimde bulunabildiği bir sosyal servis mimarisine sahiptir. Mikroservis yaklaşımıyla dış servis çağrılarına, gerçek zamanlı akışlara ve yüksek eşzamanlı isteklere uygun olarak geliştirilmiştir.

- Gönderi (entry) yönetimi  
- Gönderi yanıtları (entry_response)  
- Gönderi ve yanıt beğenileri (like_entry, like_entry_response)  
- Yazar takibi (author_follow)  

---

## Teknolojiler ve Yaklaşımlar

- **Spring WebFlux:** Event-loop mekanizması ile az sayıda thread kullanarak çok sayıda isteği işleyebilir.  
- **Kotlin Coroutines ve Flow:** Reactive zincirleri daha okunabilir ve yönetilebilir hale getirir. suspend fonksiyonlar Mono’ya, Flow ise Flux’a denk gelir. FlatMap ve flatMapMany zincirlerinden kurtulmayı sağlar.  
- **Reactive Streams Standardı:** Mono tek değer veya boş sonuç, Flux birden fazla değeri akış halinde sağlar. Backpressure desteği sayesinde veri tüketici/üretici dengesi korunur.  
- **Mikroservis Dostu:** Dış servis çağrıları ve yüksek concurrency gerektiren senaryolar için optimize edilmiştir.  

---

## Proje Yapısı
com.poslifay.Poslifay_social_service
├── controller/ # REST endpointler
├── service/ # İş mantığı ve akışlar
├── repository/ # WebClient ve veri erişim katmanı
├── config/ # WebClient bean ve konfigürasyonlar
└── model/ # Entity ve DTO sınıfları
resources/db/migration/ # Flyway migration scriptleri



---

## Neden WebFlux ve Coroutines?

- MVC modelinde her istek için thread ayrılır, yüksek trafikte darboğaz oluşur.  
- WebFlux event-loop ile I/O beklemeyen thread’ler kullanır, aynı donanımla daha fazla concurrency.  
- Kotlin Coroutines sayesinde reactive zincirler daha anlaşılır ve imperative yazım gibi kodlanabilir.  
- FlatMap ve FlatMapMany operatörleriyle uğraşmaya gerek yok, geliştirme süreci çok daha kolay.  

---

Bu proje, özellikle I/O yoğun mikroservis senaryolarında performans ve okunabilirlik sağlayan bir çözüm sunar.  
