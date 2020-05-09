-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 04, 2019 at 07:48 AM
-- Server version: 8.0.18
-- PHP Version: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `biblioteca`
--

-- --------------------------------------------------------

--
-- Table structure for table `autorxs`
--

CREATE TABLE `autorxs` (
  `id_autorx` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- Table structure for table `editoriales`
--

CREATE TABLE `editoriales` (
  `id_editorial` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- Table structure for table `entregas`
--

CREATE TABLE `entregas` (
  `id_entrega` int(11) NOT NULL,
  `estudiante` int(11) NOT NULL,
  `libro` varchar(20) NOT NULL,
  `retirado` date NOT NULL,
  `expira` date NOT NULL,
  `devuelta` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Triggers `entregas`
--
DELIMITER $$
CREATE TRIGGER `entrega_devuelta_stock` BEFORE UPDATE ON `entregas` FOR EACH ROW UPDATE libros SET stock_disponible=stock_disponible+1 WHERE NEW.devuelta=1 AND libros.ISBN = NEW.libro
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `entrega_libro_popularidad` BEFORE INSERT ON `entregas` FOR EACH ROW UPDATE libros SET libros.popularidad = libros.popularidad+1 WHERE libros.ISBN = NEW.libro
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `nueva_entrega_stock` AFTER INSERT ON `entregas` FOR EACH ROW UPDATE libros SET stock_disponible=stock_disponible-1 WHERE libros.ISBN = NEW.libro
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `estudiantes`
--

CREATE TABLE `estudiantes` (
  `DNI` int(11) NOT NULL,
  `nombre_apellido` varchar(100) NOT NULL,
  `curso` smallint(6) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `fecha_alta` date NOT NULL,
  `user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `estudiantes`
--

-- --------------------------------------------------------

--
-- Table structure for table `libros`
--

CREATE TABLE `libros` (
  `ISBN` varchar(20) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `editorial` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `stock_disponible` int(11) NOT NULL,
  `ano_original` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'Desconocido',
  `fecha_alta` date NOT NULL,
  `ultimo_restock` date DEFAULT NULL,
  `popularidad` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `libros_autorxs`
--

CREATE TABLE `libros_autorxs` (
  `ISBN_libro` varchar(20) NOT NULL,
  `id_autorx` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- Table structure for table `reservas`
--

CREATE TABLE `reservas` (
  `id_reserva` int(11) NOT NULL,
  `estudiante` int(11) NOT NULL,
  `libro` varchar(20) NOT NULL,
  `alta` date NOT NULL,
  `expira` date NOT NULL,
  `cant_dias` int(11) NOT NULL,
  `efectiva` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Triggers `reservas`
--
DELIMITER $$
CREATE TRIGGER `borrar_reserva_libro_popularidad` BEFORE INSERT ON `reservas` FOR EACH ROW UPDATE libros SET libros.popularidad = libros.popularidad-1 WHERE libros.ISBN = NEW.libro
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `borrar_reserva_stock` AFTER DELETE ON `reservas` FOR EACH ROW UPDATE libros SET stock_disponible = stock_disponible+1 WHERE libros.ISBN = OLD.libro
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `nueva_reserva_libros` AFTER INSERT ON `reservas` FOR EACH ROW UPDATE libros SET stock_disponible = stock_disponible-1 WHERE ISBN = NEW.libro
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `reserva_cancelada_stock` AFTER UPDATE ON `reservas` FOR EACH ROW UPDATE libros SET stock_disponible = stock_disponible+1 WHERE NEW.efectiva = 0  AND libros.ISBN = NEW.libro
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `reserva_libro_popularidad` AFTER INSERT ON `reservas` FOR EACH ROW UPDATE libros SET libros.popularidad = libros.popularidad+1 WHERE libros.ISBN = NEW.libro
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `pass` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tipo_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `username`, `pass`, `tipo_usuario`) VALUES
(13, 'admin', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 3),

--
-- Indexes for dumped tables
--

--
-- Indexes for table `autorxs`
--
ALTER TABLE `autorxs`
  ADD PRIMARY KEY (`id_autorx`);

--
-- Indexes for table `editoriales`
--
ALTER TABLE `editoriales`
  ADD PRIMARY KEY (`id_editorial`),
  ADD UNIQUE KEY `nombre_UNIQUE` (`nombre`);

--
-- Indexes for table `entregas`
--
ALTER TABLE `entregas`
  ADD PRIMARY KEY (`id_entrega`),
  ADD KEY `entregas_estudiante` (`estudiante`),
  ADD KEY `entregas_libro` (`libro`);

--
-- Indexes for table `estudiantes`
--
ALTER TABLE `estudiantes`
  ADD PRIMARY KEY (`DNI`),
  ADD KEY `estudiantes_user` (`user`);

--
-- Indexes for table `libros`
--
ALTER TABLE `libros`
  ADD PRIMARY KEY (`ISBN`),
  ADD KEY `libros_editorial` (`editorial`);

--
-- Indexes for table `libros_autorxs`
--
ALTER TABLE `libros_autorxs`
  ADD PRIMARY KEY (`ISBN_libro`,`id_autorx`),
  ADD KEY `libros_autorxs_autorx` (`id_autorx`);

--
-- Indexes for table `reservas`
--
ALTER TABLE `reservas`
  ADD PRIMARY KEY (`id_reserva`),
  ADD KEY `reservas_estudiante` (`estudiante`),
  ADD KEY `reservas_libro` (`libro`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username_UNIQUE` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `autorxs`
--
ALTER TABLE `autorxs`
  MODIFY `id_autorx` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `editoriales`
--
ALTER TABLE `editoriales`
  MODIFY `id_editorial` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `entregas`
--
ALTER TABLE `entregas`
  MODIFY `id_entrega` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `reservas`
--
ALTER TABLE `reservas`
  MODIFY `id_reserva` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `entregas`
--
ALTER TABLE `entregas`
  ADD CONSTRAINT `entregas_estudiante` FOREIGN KEY (`estudiante`) REFERENCES `estudiantes` (`DNI`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `entregas_libro` FOREIGN KEY (`libro`) REFERENCES `libros` (`ISBN`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `estudiantes`
--
ALTER TABLE `estudiantes`
  ADD CONSTRAINT `estudiantes_user` FOREIGN KEY (`user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `libros`
--
ALTER TABLE `libros`
  ADD CONSTRAINT `libros_editorial` FOREIGN KEY (`editorial`) REFERENCES `editoriales` (`id_editorial`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `libros_autorxs`
--
ALTER TABLE `libros_autorxs`
  ADD CONSTRAINT `libros_autorxs_autorx` FOREIGN KEY (`id_autorx`) REFERENCES `autorxs` (`id_autorx`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `libros_autorxs_libro` FOREIGN KEY (`ISBN_libro`) REFERENCES `libros` (`ISBN`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reservas`
--
ALTER TABLE `reservas`
  ADD CONSTRAINT `reservas_estudiante` FOREIGN KEY (`estudiante`) REFERENCES `estudiantes` (`DNI`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservas_libro` FOREIGN KEY (`libro`) REFERENCES `libros` (`ISBN`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
