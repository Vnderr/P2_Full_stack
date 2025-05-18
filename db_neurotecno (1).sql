-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 18-05-2025 a las 18:27:07
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_neurotecno`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `atencion`
--

CREATE TABLE `atencion` (
  `id` int(11) NOT NULL,
  `comentario` varchar(255) NOT NULL,
  `costo` int(11) NOT NULL,
  `fecha_atencion` date NOT NULL,
  `hora_atencion` time(6) NOT NULL,
  `medico_id` int(11) NOT NULL,
  `paciente_id` int(11) NOT NULL,
  `tipousuario_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `atencion`
--

INSERT INTO `atencion` (`id`, `comentario`, `costo`, `fecha_atencion`, `hora_atencion`, `medico_id`, `paciente_id`, `tipousuario_id`) VALUES
(1, 'Consulta general ', 25000, '2025-03-09', '09:30:00.000000', 1, 1, 1),
(2, 'Reagenda Control mensual', 30000, '2025-05-11', '10:00:00.000000', 1, 2, 2),
(3, 'Evaluación intermedia', 50000, '2025-04-29', '11:00:00.000000', 1, 3, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `medico`
--

CREATE TABLE `medico` (
  `id` int(11) NOT NULL,
  `especialidad` varchar(255) NOT NULL,
  `jefe_turno` varchar(255) NOT NULL,
  `nombre_completo` varchar(255) NOT NULL,
  `run` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `medico`
--

INSERT INTO `medico` (`id`, `especialidad`, `jefe_turno`, `nombre_completo`, `run`) VALUES
(1, 'Social', 'Pedro Pablo', 'Daniel Funtes', '10.058.630-1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paciente`
--

CREATE TABLE `paciente` (
  `id` int(11) NOT NULL,
  `apellidos` varchar(255) NOT NULL,
  `correo` varchar(255) NOT NULL,
  `fecha_nacimiento` datetime(6) DEFAULT NULL,
  `nombres` varchar(255) NOT NULL,
  `run` varchar(13) NOT NULL,
  `tipo_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `paciente`
--

INSERT INTO `paciente` (`id`, `apellidos`, `correo`, `fecha_nacimiento`, `nombres`, `run`, `tipo_usuario`) VALUES
(1, 'Sanchez', 'sanchez@gmail.com', '2000-04-15 00:00:00.000000', 'Alexis', '18.569.332-k', 1),
(2, 'Vidal', 'vidal@gmail.com', '1996-09-01 00:00:00.000000', 'Arturo', '19.569.332-k', 2),
(3, 'Isla', 'isla@gmail.com', '1999-07-18 00:00:00.000000', 'Mauricio', '20.569.332-k', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_usuario`
--

CREATE TABLE `tipo_usuario` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tipo_usuario`
--

INSERT INTO `tipo_usuario` (`id`, `nombre`) VALUES
(1, 'Cliente'),
(2, 'Soporte'),
(3, 'Medico');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `atencion`
--
ALTER TABLE `atencion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKoj6awe64e7s67fjy0cwa3grnp` (`medico_id`),
  ADD KEY `FKg276pvov40ep8j5o8uiro4dn` (`paciente_id`),
  ADD KEY `FKarjfcygn74g4maxulak3g93oa` (`tipousuario_id`);

--
-- Indices de la tabla `medico`
--
ALTER TABLE `medico`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKpaovfh4julpeh6bbnxistydta` (`run`);

--
-- Indices de la tabla `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKhkn138kpjgyff23mnl24ihokw` (`run`),
  ADD KEY `FKdggd868hmu0pehmfqupcv12kn` (`tipo_usuario`);

--
-- Indices de la tabla `tipo_usuario`
--
ALTER TABLE `tipo_usuario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `atencion`
--
ALTER TABLE `atencion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `medico`
--
ALTER TABLE `medico`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `paciente`
--
ALTER TABLE `paciente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `tipo_usuario`
--
ALTER TABLE `tipo_usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `atencion`
--
ALTER TABLE `atencion`
  ADD CONSTRAINT `FKarjfcygn74g4maxulak3g93oa` FOREIGN KEY (`tipousuario_id`) REFERENCES `tipo_usuario` (`id`),
  ADD CONSTRAINT `FKg276pvov40ep8j5o8uiro4dn` FOREIGN KEY (`paciente_id`) REFERENCES `paciente` (`id`),
  ADD CONSTRAINT `FKoj6awe64e7s67fjy0cwa3grnp` FOREIGN KEY (`medico_id`) REFERENCES `medico` (`id`);

--
-- Filtros para la tabla `paciente`
--
ALTER TABLE `paciente`
  ADD CONSTRAINT `FKdggd868hmu0pehmfqupcv12kn` FOREIGN KEY (`tipo_usuario`) REFERENCES `tipo_usuario` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
