**Nama:** Adjeng Mutiara Dewi
**NIM:** H1D024055
**Shift Baru:** I
**Shift KRS:** B
**Responsi:** 1 - Praktikum Pemrograman Mobile

# Paket 1 = Pantau Gempa BMKG - Aplikasi Monitoring & Katalog Gempa Terkini

Aplikasi mobile Android berbasis **Jetpack Compose** untuk menampilkan katalog dan pemantauan gempa bumi terkini di seluruh wilayah Indonesia secara *real-time* langsung dari REST API resmi Badan Meteorologi, Klimatologi, dan Geofisika (BMKG).

---

## Screenshot Aplikasi

<table>
  <tr>
    <td align="center"><b>Halaman Katalog Gempa</b></td>
    <td align="center"><b>Halaman Detail Gempa</b></td>
  </tr>
  <tr>
    <td align="center">
      <img src="outputs/katalog.jpeg" width="280">
    </td>
    <td align="center">
      <img src="outputs/detail.jpeg" width="280">
    </td>
  </tr>
</table>

---

## Fitur Aplikasi

1. **Katalog Gempa Terkini**: Menampilkan daftar gempa bumi M 5.0+ terbaru dari BMKG menggunakan `LazyColumn`.
2. **Pencarian / Filter Lokal Wilayah**: Fitur pencarian lokasi wilayah gempa secara instan dan responsif.
3. **Detail Informasi Gempa**: Menampilkan rincian parameter lengkap setiap kejadian gempa meliputi Tanggal, Jam, Koordinat, Magnitudo, Kedalaman, Wilayah, dan Potensi Tsunami.
4. **Indikator Tingkat Bahaya (Warna Dinamis)**: Badge magnitudo otomatis menyesuaikan warna status keparahan gempa (*Low*, *Warning*, *Significant*, *Critical*).
5. **State-Driven UI**: Penanganan state UI yang tangguh mencakup *Loading State*, *Success State*, dan *Error State* disertai tombol *Retry* (*Coba Lagi*).

---

## Arsitektur Aplikasi (MVVM Pattern)

Aplikasi dibangun dengan mematuhi pola arsitektur **Model-View-ViewModel (MVVM)**:

```
┌──────────────────────────────────────────────┐
│             View / UI Layer                  │
│  - KatalogGempaScreen (Home Screen)          │
│  - DetailGempaScreen (Detail Screen)         │
│  - GempaItemCard (Reusable Composable)       │
└──────────────────────┬───────────────────────┘
                       │ Observers StateFlow
                       ▼
┌──────────────────────────────────────────────┐
│              ViewModel Layer                 │
│  - GempaViewModel                            │
│  - GempaUiState (sealed interface)           │
└──────────────────────┬───────────────────────┘
                       │ Memanggil data
                       ▼
┌──────────────────────────────────────────────┐
│             Repository Layer                 │
│  - GempaRepository                           │
└──────────────────────┬───────────────────────┘
                       │ Mengakses jaringan
                       ▼
┌──────────────────────────────────────────────┐
│           Network / Remote Data              │
│  - Retrofit & ApiService (Gson Converter)    │
│  - REST API BMKG                             │
└──────────────────────────────────────────────┘
```

- **View / Composable**: Bertanggung jawab menampilkan antarmuka pengguna secara deklaratif melalui Jetpack Compose tanpa memanggil API langsung.
- **ViewModel**: Mengelola state aplikasi (`GempaUiState`) menggunakan `StateFlow` dan menangani coroutine scope (`viewModelScope`).
- **Repository**: Mengabstraksi sumber data dari API Retrofit untuk diteruskan ke ViewModel.
- **Retrofit & ApiService**: Menangani komunikasi HTTP dan serialisasi respons JSON dari BMKG ke Data Model Kotlin.

---

## Sumber Data & API

- **Penyedia Data**: Badan Meteorologi, Klimatologi, dan Geofisika (BMKG)
- **Base URL**: `https://data.bmkg.go.id/`
- **Endpoint**: `DataMKG/TEWS/gempaterkini.json`
- **Full URL**: `https://data.bmkg.go.id/DataMKG/TEWS/gempaterkini.json`
- **Struktur JSON**:
  ```json
  {
    "Infogempa": {
      "gempa": [
        {
          "Tanggal": "06 Okt 2026",
          "Jam": "20:11:23 WIB",
          "DateTime": "2026-10-06T13:11:23+00:00",
          "Coordinates": "4.94,118.77",
          "Lintang": "4.94 LU",
          "Bujur": "118.77 BT",
          "Magnitude": "5.3",
          "Kedalaman": "10 km",
          "Wilayah": "219 km TimurLaut TARAKAN-KALTARA",
          "Potensi": "Tidak berpotensi tsunami"
        }
      ]
    }
  }
  ```

---

## Spesifikasi Teknis & Dependencies

- **Bahasa Pemrograman**: Kotlin
  - Pemanfaatan *Data Class* (`GempaItem`, `InfoGempa`, `Gempa`)
  - Pemanfaatan *Null Safety*
  - Pemanfaatan *Lambda* dan *High-Order Function*
  - Pemanfaatan *Extension Function* (`GempaItem.formattedMagnitude()`, `GempaItem.formattedWaktu()`)
- **UI Toolkit**: Jetpack Compose & Material Design 3
  - Scaffold + TopAppBar
  - Custom Color Scheme (`Color.kt`, `Theme.kt` dengan Light & Dark mode)
  - Custom Typography (`Type.kt`)
  - Lazy Layout (`LazyColumn`)
- **Navigasi**: Navigation Compose (maksimal 2 screen: Home Screen & Detail Screen)
- **Networking & Serialization**:
  - `Retrofit` (v3.0.0)
  - `Converter Gson` (v3.0.0)
- **Izin Aplikasi**:
  - `android.permission.INTERNET` di `AndroidManifest.xml`
- **Sesuai Batasan Tugas**:
  - Tanpa library pemuat gambar (tidak ada Coil / Glide)
  - Tanpa pemanggilan API langsung pada Composable
