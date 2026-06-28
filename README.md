# Aplikasi Berkah Jaya
## Deskripsi

Berkah Jaya merupakan aplikasi desktop berbasis Java yang digunakan untuk mengelola proses penjualan pada sebuah toko. Aplikasi ini dibangun menggunakan Java Swing sebagai antarmuka pengguna dan MySQL sebagai database.

Aplikasi menyediakan fitur pengelolaan data barang, kategori, customer, transaksi penjualan, serta laporan penjualan sehingga memudahkan proses administrasi toko.

---

## Fitur

- Login Admin
- Dashboard/Menu Utama
- Manajemen Data Barang
- Manajemen Data Customer
- Manajemen Kategori Barang
- Transaksi Penjualan
- Perhitungan Total Pembelian
- Update Stok Otomatis
- Penghapusan Transaksi dengan Restore Stok
- Laporan Penjualan
- Papan Kasir
- Perhitungan Kembalian
- Cetak Struk

---

## Teknologi

- Java
- Java Swing
- JDBC
- MySQL
- NetBeans IDE
- MySQL Connector/J

---

## Struktur Project

```
BerkahJaya
│
├── FormLogin.java
├── FormMenu.java
├── FormBarang.java
├── FormCustomer.java
├── FormKategori.java
├── FormTransaksi.java
├── FormLaporan.java
├── Koneksi.java
└── database.sql
```

---

## Database

Nama database:

```
db_toko
```

Konfigurasi database berada pada file:

```
Koneksi.java
```

```java
String url = "jdbc:mysql://localhost:3306/db_toko";
String user = "root";
String pass = "";
```

---

## Cara Menjalankan

1. Install JDK 17 atau yang lebih baru.
2. Install MySQL Server.
3. Import database `db_toko`.
4. Tambahkan library MySQL Connector ke project.
5. Jalankan file:

```
FormLogin.java
```

---

## Modul Aplikasi

### Login
Digunakan untuk autentikasi pengguna sebelum masuk ke aplikasi.

### Dashboard
Berisi menu menuju seluruh fitur aplikasi.

### Data Barang

- Tambah Barang
- Hapus Barang
- Cari Barang
- Sort Harga
- Tambah Kategori

### Data Customer

- Tambah Customer
- Hapus Customer
- Cari Customer

### Data Kategori

- Tambah Kategori
- Hapus Kategori
- Cari Kategori

### Transaksi

- Memilih Customer
- Memilih Barang
- Menghitung Total
- Menyimpan Transaksi
- Mengurangi Stok Barang

### Laporan

- Menampilkan seluruh transaksi
- Menampilkan total penjualan
- Menghitung pembayaran
- Menghitung kembalian
- Cetak struk transaksi

---

## Kebutuhan Sistem

- Windows 10/11
- Java JDK
- NetBeans IDE
- MySQL
- MySQL Connector/J

---

## Pengembang

Dikembangkan sebagai proyek aplikasi Sistem Informasi Penjualan Berkah Jaya.

Developer:

Dewi Andrayani

Universitas Pamulang

Tahun: 2026

---

## Lisensi

Project ini dibuat untuk keperluan pembelajaran dan tugas akademik.
