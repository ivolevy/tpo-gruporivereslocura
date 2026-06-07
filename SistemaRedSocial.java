import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class SistemaRedSocial extends JFrame {
    Diccionario perfiles;
    Pila historial;
    Cola postulaciones;
    Arbol habilidades;
    Grafo redContactos;

    JComboBox comboUserA;
    JComboBox comboUserB;
    JComboBox comboUserInicio;
    JComboBox comboPostulante;
    JComboBox comboUserHab;
    JComboBox comboUserSepA;
    JComboBox comboUserSepB;

    JTextArea areaPerfiles;
    JTextArea areaContactos;
    JTextArea areaHabilidades;
    JTextArea areaEmpleos;
    JTextArea areaHistorial;

    JTextField txtPerfilId;
    JTextField txtPerfilNombre;
    JTextField txtPerfilProfesion;
    JTextField txtPerfilHabilidad;
    JTextField txtBuscarId;
    JTextField txtHabilidad;
    JTextField txtBuscarEspecialidad;
    JTextField txtHabilidadesRequeridas;

    Color colorFondo = new Color(245, 245, 245);
    Color colorPanel = new Color(255, 255, 255);
    Color colorTexto = new Color(30, 30, 30);
    Color colorInput = new Color(255, 255, 255);
    Color colorInputBorder = new Color(200, 200, 200);
    Color colorBoton = new Color(229, 26, 34);
    Color colorBotonSecundario = new Color(40, 40, 40);
    Color colorBordePanel = new Color(220, 220, 220);
    
    Font fontGlobal = new Font("SansSerif", Font.PLAIN, 13);
    Font fontNegrita = new Font("SansSerif", Font.BOLD, 13);
    Font fontTitulo = new Font("SansSerif", Font.BOLD, 16);

    class HeaderRiverPlate extends JPanel {
        private Image shieldImg;

        public HeaderRiverPlate() {
            setPreferredSize(new Dimension(850, 75));
            setBackground(Color.WHITE);
            try {
                shieldImg = new ImageIcon("river_shield.png").getImage();
            } catch (Exception e) {
                shieldImg = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, w, h);

            g2.setColor(colorBoton);
            Polygon sash = new Polygon();
            sash.addPoint(w - 280, 0);
            sash.addPoint(w - 190, 0);
            sash.addPoint(w - 50, h);
            sash.addPoint(w - 140, h);
            g2.fill(sash);

            g2.setColor(colorBordePanel);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(0, h - 1, w, h - 1);

            if (shieldImg != null) {
                g2.drawImage(shieldImg, 15, 8, 58, 58, this);
            } else {
                drawShield(g2, 20, 12, 42, 50);
            }

            g2.setColor(colorTexto);
            g2.setFont(new Font("SansSerif", Font.BOLD, 18));
            FontMetrics fm = g2.getFontMetrics();
            int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString("ECOSISTEMA RED SOCIAL", 85, ty);
        }

        private void drawShield(Graphics2D g2, int x, int y, int width, int height) {
            int w = width;
            int h = height;

            Path2D shieldPath = new Path2D.Double();
            shieldPath.moveTo(x + w * 0.1, y);
            shieldPath.lineTo(x + w * 0.9, y);
            shieldPath.quadTo(x + w, y + h * 0.4, x + w * 0.5, y + h);
            shieldPath.quadTo(x, y + h * 0.4, x + w * 0.1, y);
            shieldPath.closePath();

            g2.setColor(Color.WHITE);
            g2.fill(shieldPath);

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2.5f));
            g2.draw(shieldPath);

            Path2D innerBorder = new Path2D.Double();
            innerBorder.moveTo(x + w * 0.16, y + h * 0.06);
            innerBorder.lineTo(x + w * 0.84, y + h * 0.06);
            innerBorder.quadTo(x + w * 0.92, y + h * 0.42, x + w * 0.5, y + h * 0.92);
            innerBorder.quadTo(x + w * 0.08, y + h * 0.42, x + w * 0.16, y + h * 0.06);
            g2.setStroke(new BasicStroke(1f));
            g2.draw(innerBorder);

            g2.setColor(colorBoton);
            Area shieldArea = new Area(innerBorder);

            Path2D sashPath = new Path2D.Double();
            sashPath.moveTo(x + w * 0.15, y + h * 0.15);
            sashPath.lineTo(x + w * 0.35, y + h * 0.08);
            sashPath.lineTo(x + w * 0.85, y + h * 0.75);
            sashPath.lineTo(x + w * 0.65, y + h * 0.82);
            sashPath.closePath();

            Area sashArea = new Area(sashPath);
            sashArea.intersect(shieldArea);
            g2.fill(sashArea);

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 10));
            FontMetrics fm = g2.getFontMetrics();
            String text = "CARP";
            int tx = x + (w - fm.stringWidth(text)) / 2;
            int ty = y + (h + fm.getAscent() - fm.getDescent()) / 2 + 1;
            g2.drawString(text, tx, ty);
        }
    }

    public SistemaRedSocial() {
        perfiles = new Diccionario(100);
        historial = new Pila(100);
        postulaciones = new Cola(100);
        habilidades = new Arbol();
        redContactos = new Grafo(200);

        setTitle("Ecosistema Red Social Profesional - Edición Moriste en Madrid");
        setSize(850, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        UIManager.put("TabbedPane.background", colorFondo);
        UIManager.put("TabbedPane.foreground", colorTexto);
        UIManager.put("TabbedPane.selected", colorPanel);
        UIManager.put("TabbedPane.font", fontNegrita);

        JTabbedPane pestanas = new JTabbedPane();

        JPanel panelPerfiles = crearPanelPerfiles();
        JPanel panelContactos = crearPanelContactos();
        JPanel panelHabilidades = crearPanelHabilidades();
        JPanel panelEmpleos = crearPanelEmpleos();
        JPanel panelHistorial = crearPanelHistorial();

        pestanas.addTab("Perfiles", panelPerfiles);
        pestanas.addTab("Red", panelContactos);
        pestanas.addTab("Habilidades", panelHabilidades);
        pestanas.addTab("Postulaciones", panelEmpleos);
        pestanas.addTab("Historial", panelHistorial);

        JPanel contenedorPrincipal = new JPanel(new BorderLayout());
        HeaderRiverPlate header = new HeaderRiverPlate();
        contenedorPrincipal.add(header, BorderLayout.NORTH);
        contenedorPrincipal.add(pestanas, BorderLayout.CENTER);

        getContentPane().setBackground(colorFondo);
        add(contenedorPrincipal);

        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        perfiles.insertar(101, new Perfil(101, "Juan", "Desarrollador Java"));
        perfiles.insertar(102, new Perfil(102, "Ana", "UX/UI Designer"));
        perfiles.insertar(103, new Perfil(103, "Pedro", "QA Engineer"));
        historial.apilar(new Accion(null, null, "Se cargaron los perfiles iniciales."));
        
        redContactos.conectar(101, 102);
        redContactos.conectar(102, 103);
        
        habilidades.insertar("Tecnologia", 101);
        habilidades.insertar("Desarrollo", 101);
        habilidades.insertar("Java", 101);
        habilidades.insertar("Redes", 102);
        habilidades.insertar("Diseno", 102);
        habilidades.insertar("Testing", 103);

        actualizarAreaPerfiles();
        actualizarAreaContactos();
        actualizarAreaHabilidades();
        actualizarAreaEmpleos();
        actualizarAreaHistorial();
        actualizarCombos();
    }

    private JPanel crearPanelPerfiles() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBackground(colorFondo);
        main.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(colorPanel);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBordePanel),
            new EmptyBorder(15, 15, 15, 15)
        ));
        form.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel lblReg = new JLabel("Registrar Perfil");
        lblReg.setFont(fontTitulo);
        lblReg.setForeground(colorBoton);
        gbc.gridy = 0;
        form.add(lblReg, gbc);

        JLabel lblId = new JLabel("ID (Número único):");
        lblId.setFont(fontNegrita);
        lblId.setForeground(colorTexto);
        gbc.gridy = 1;
        form.add(lblId, gbc);

        txtPerfilId = new JTextField();
        styleTextField(txtPerfilId);
        gbc.gridy = 2;
        form.add(txtPerfilId, gbc);

        JLabel lblNom = new JLabel("Nombre Completo:");
        lblNom.setFont(fontNegrita);
        lblNom.setForeground(colorTexto);
        gbc.gridy = 3;
        form.add(lblNom, gbc);

        txtPerfilNombre = new JTextField();
        styleTextField(txtPerfilNombre);
        gbc.gridy = 4;
        form.add(txtPerfilNombre, gbc);

        JLabel lblProf = new JLabel("Profesión:");
        lblProf.setFont(fontNegrita);
        lblProf.setForeground(colorTexto);
        gbc.gridy = 5;
        form.add(lblProf, gbc);

        txtPerfilProfesion = new JTextField();
        styleTextField(txtPerfilProfesion);
        gbc.gridy = 6;
        form.add(txtPerfilProfesion, gbc);

        JLabel lblHabOblig = new JLabel("Habilidad Obligatoria:");
        lblHabOblig.setFont(fontNegrita);
        lblHabOblig.setForeground(colorTexto);
        gbc.gridy = 7;
        form.add(lblHabOblig, gbc);

        txtPerfilHabilidad = new JTextField();
        styleTextField(txtPerfilHabilidad);
        gbc.gridy = 8;
        form.add(txtPerfilHabilidad, gbc);

        JButton btnRegistrar = new JButton("Registrar Perfil");
        styleButton(btnRegistrar, colorBoton);
        gbc.gridy = 9;
        form.add(btnRegistrar, gbc);

        JSeparator sep = new JSeparator();
        sep.setForeground(colorBordePanel);
        gbc.gridy = 10;
        form.add(sep, gbc);

        JLabel lblBusc = new JLabel("Buscar Perfil");
        lblBusc.setFont(fontTitulo);
        lblBusc.setForeground(colorBoton);
        gbc.gridy = 11;
        form.add(lblBusc, gbc);

        JLabel lblBuscarId = new JLabel("ID a buscar:");
        lblBuscarId.setFont(fontNegrita);
        lblBuscarId.setForeground(colorTexto);
        gbc.gridy = 12;
        form.add(lblBuscarId, gbc);

        txtBuscarId = new JTextField();
        styleTextField(txtBuscarId);
        gbc.gridy = 13;
        form.add(txtBuscarId, gbc);

        JButton btnBuscar = new JButton("Buscar");
        styleButton(btnBuscar, colorBotonSecundario);
        gbc.gridy = 14;
        form.add(btnBuscar, gbc);

        areaPerfiles = new JTextArea();
        styleTextArea(areaPerfiles);
        JScrollPane scroll = new JScrollPane(areaPerfiles);
        scroll.setBorder(BorderFactory.createLineBorder(colorBordePanel));

        main.add(form, BorderLayout.WEST);
        main.add(scroll, BorderLayout.CENTER);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgs) {
                try {
                    int id = Integer.parseInt(txtPerfilId.getText().trim());
                    if (id < 0 || id >= 200) {
                        JOptionPane.showMessageDialog(SistemaRedSocial.this, "El ID debe estar entre 0 y 199 para ser compatible con el grafo de la red.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String nombre = txtPerfilNombre.getText().trim();
                    String profesion = txtPerfilProfesion.getText().trim();
                    String habilidad = txtPerfilHabilidad.getText().trim();
                    if (nombre.isEmpty() || profesion.isEmpty() || habilidad.isEmpty()) {
                        JOptionPane.showMessageDialog(SistemaRedSocial.this, "Complete todos los campos, incluyendo la habilidad obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (perfiles.recuperar(id) != null) {
                        JOptionPane.showMessageDialog(SistemaRedSocial.this, "El ID ya se encuentra registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    boolean insertado = perfiles.insertar(id, new Perfil(id, nombre, profesion));
                    if (!insertado) {
                        JOptionPane.showMessageDialog(SistemaRedSocial.this, "No se pudo registrar el perfil. El diccionario está lleno.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    habilidades.insertar(habilidad, id);
                    boolean apilado = historial.apilar(new Accion(
                        Accion.Tipo.REGISTRAR_PERFIL,
                        new Object[]{ id, habilidad },
                        "Se registró a " + nombre + " con ID " + id + " y habilidad " + habilidad + "."
                    ));
                    if (!apilado) {
                        JOptionPane.showMessageDialog(SistemaRedSocial.this, "Advertencia: El historial de acciones está lleno. La acción se completó pero no se podrá deshacer.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                    txtPerfilId.setText("");
                    txtPerfilNombre.setText("");
                    txtPerfilProfesion.setText("");
                    txtPerfilHabilidad.setText("");
                    actualizarAreaPerfiles();
                    actualizarAreaHabilidades();
                    actualizarAreaHistorial();
                    actualizarCombos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "El ID debe ser un valor numérico.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgs) {
                try {
                    int id = Integer.parseInt(txtBuscarId.getText().trim());
                    Perfil p = perfiles.recuperar(id);
                    if (p != null) {
                        areaPerfiles.setText("=== RESULTADO DE BÚSQUEDA ===\n\n" + p.toString() + "\n\n" + areaPerfiles.getText());
                    } else {
                        JOptionPane.showMessageDialog(SistemaRedSocial.this, "ID no encontrado en el diccionario.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                    txtBuscarId.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "El ID debe ser un valor numérico.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return main;
    }

    private JPanel crearPanelContactos() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBackground(colorFondo);
        main.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(colorPanel);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBordePanel),
            new EmptyBorder(15, 15, 15, 15)
        ));
        form.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel lblCon = new JLabel("Establecer Conexión");
        lblCon.setFont(fontTitulo);
        lblCon.setForeground(colorBoton);
        gbc.gridy = 0;
        form.add(lblCon, gbc);

        JLabel lblUserA = new JLabel("Usuario A:");
        lblUserA.setFont(fontNegrita);
        lblUserA.setForeground(colorTexto);
        gbc.gridy = 1;
        form.add(lblUserA, gbc);

        comboUserA = new JComboBox();
        styleComboBox(comboUserA);
        gbc.gridy = 2;
        form.add(comboUserA, gbc);

        JLabel lblUserB = new JLabel("Usuario B:");
        lblUserB.setFont(fontNegrita);
        lblUserB.setForeground(colorTexto);
        gbc.gridy = 3;
        form.add(lblUserB, gbc);

        comboUserB = new JComboBox();
        styleComboBox(comboUserB);
        gbc.gridy = 4;
        form.add(comboUserB, gbc);

        JButton btnConectar = new JButton("Conectar");
        styleButton(btnConectar, colorBoton);
        gbc.gridy = 5;
        form.add(btnConectar, gbc);

        JSeparator sep = new JSeparator();
        sep.setForeground(colorBordePanel);
        gbc.gridy = 6;
        form.add(sep, gbc);

        JLabel lblRecom = new JLabel("Sugerir Contactos BFS");
        lblRecom.setFont(fontTitulo);
        lblRecom.setForeground(colorBoton);
        gbc.gridy = 7;
        form.add(lblRecom, gbc);

        JLabel lblUserInicio = new JLabel("Usuario de inicio:");
        lblUserInicio.setFont(fontNegrita);
        lblUserInicio.setForeground(colorTexto);
        gbc.gridy = 8;
        form.add(lblUserInicio, gbc);

        comboUserInicio = new JComboBox();
        styleComboBox(comboUserInicio);
        gbc.gridy = 9;
        form.add(comboUserInicio, gbc);

        JButton btnBuscarBFS = new JButton("Buscar Recomendados");
        styleButton(btnBuscarBFS, colorBotonSecundario);
        gbc.gridy = 10;
        form.add(btnBuscarBFS, gbc);

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(colorBordePanel);
        gbc.gridy = 11;
        form.add(sep2, gbc);

        JLabel lblSep = new JLabel("Grado de Separación");
        lblSep.setFont(fontTitulo);
        lblSep.setForeground(colorBoton);
        gbc.gridy = 12;
        form.add(lblSep, gbc);

        JLabel lblUserSepA = new JLabel("Usuario Origen:");
        lblUserSepA.setFont(fontNegrita);
        lblUserSepA.setForeground(colorTexto);
        gbc.gridy = 13;
        form.add(lblUserSepA, gbc);

        comboUserSepA = new JComboBox();
        styleComboBox(comboUserSepA);
        gbc.gridy = 14;
        form.add(comboUserSepA, gbc);

        JLabel lblUserSepB = new JLabel("Usuario Destino:");
        lblUserSepB.setFont(fontNegrita);
        lblUserSepB.setForeground(colorTexto);
        gbc.gridy = 15;
        form.add(lblUserSepB, gbc);

        comboUserSepB = new JComboBox();
        styleComboBox(comboUserSepB);
        gbc.gridy = 16;
        form.add(comboUserSepB, gbc);

        JButton btnCalcularSep = new JButton("Calcular Grado");
        styleButton(btnCalcularSep, colorBoton);
        gbc.gridy = 17;
        form.add(btnCalcularSep, gbc);

        areaContactos = new JTextArea();
        styleTextArea(areaContactos);
        JScrollPane scroll = new JScrollPane(areaContactos);
        scroll.setBorder(BorderFactory.createLineBorder(colorBordePanel));

        main.add(form, BorderLayout.WEST);
        main.add(scroll, BorderLayout.CENTER);

        btnCalcularSep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgs) {
                String itemA = (String) comboUserSepA.getSelectedItem();
                String itemB = (String) comboUserSepB.getSelectedItem();
                if (itemA == null || itemB == null) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Seleccione dos perfiles.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idA = Integer.parseInt(itemA.split(" - ")[0]);
                int idB = Integer.parseInt(itemB.split(" - ")[0]);
                if (idA == idB) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Seleccione dos perfiles distintos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int grado = redContactos.gradoSeparacion(idA, idB);
                String resultText;
                if (grado == -1) {
                    resultText = "No existe conexión entre " + itemA + " y " + itemB + ".";
                } else if (grado == 1) {
                    resultText = "El grado de separación entre " + itemA + " y " + itemB + " es 1 (Amigos directos).";
                } else if (grado == 2) {
                    resultText = "El grado de separación entre " + itemA + " y " + itemB + " es 2 (Amigos de amigos).";
                } else {
                    resultText = "El grado de separación entre " + itemA + " y " + itemB + " es " + grado + ".";
                }
                areaContactos.setText("=== GRADO DE SEPARACIÓN ===\n" + resultText + "\n\n" + areaContactos.getText());
            }
        });

        btnConectar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgs) {
                String itemA = (String) comboUserA.getSelectedItem();
                String itemB = (String) comboUserB.getSelectedItem();
                if (itemA == null || itemB == null) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Seleccione dos perfiles.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idA = Integer.parseInt(itemA.split(" - ")[0]);
                int idB = Integer.parseInt(itemB.split(" - ")[0]);
                if (idA == idB) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "No puede conectar un perfil consigo mismo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (redContactos.matriz[idA][idB] == 1) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Estos perfiles ya se encuentran conectados.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                redContactos.conectar(idA, idB);
                boolean apilado = historial.apilar(new Accion(
                    Accion.Tipo.CONECTAR_CONTACTOS,
                    new Object[]{ idA, idB },
                    "Se conectó el ID " + idA + " con el ID " + idB + "."
                ));
                if (!apilado) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Advertencia: El historial de acciones está lleno. La acción se completó pero no se podrá deshacer.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
                actualizarAreaContactos();
                actualizarAreaHistorial();
            }
        });

        btnBuscarBFS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgs) {
                String item = (String) comboUserInicio.getSelectedItem();
                if (item == null) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Seleccione un perfil de origen.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int id = Integer.parseInt(item.split(" - ")[0]);
                String sug = redContactos.sugerirContactosBFS(id);
                StringBuilder sb = new StringBuilder();
                sb.append("=== RECOMENDACIONES DE 2DO NIVEL PARA ").append(item).append(" ===\n\n");
                if (sug.trim().isEmpty()) {
                    sb.append("(No se encontraron sugerencias por BFS)\n");
                } else {
                    String[] lines = sug.split("\n");
                    for (String line : lines) {
                        if (line.startsWith("ID sugerido: ")) {
                            int sugId = Integer.parseInt(line.substring(13).trim());
                            Perfil p = perfiles.recuperar(sugId);
                            if (p != null) {
                                sb.append("- ").append(p.nombre).append(" (ID ").append(sugId).append(" - ").append(p.profesion).append(")\n");
                            } else {
                                sb.append("- ID sugerido: ").append(sugId).append("\n");
                            }
                        }
                    }
                }
                areaContactos.setText(sb.toString() + "\n" + areaContactos.getText());
                boolean apilado = historial.apilar(new Accion(
                    null,
                    null,
                    "Búsqueda BFS realizada para el ID " + id + "."
                ));
                if (!apilado) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Advertencia: El historial de acciones está lleno. La acción se completó pero no se podrá deshacer.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
                actualizarAreaHistorial();
            }
        });

        return main;
    }

    private JPanel crearPanelHabilidades() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBackground(colorFondo);
        main.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(colorPanel);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBordePanel),
            new EmptyBorder(15, 15, 15, 15)
        ));
        form.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel lblHab = new JLabel("Registrar Habilidad");
        lblHab.setFont(fontTitulo);
        lblHab.setForeground(colorBoton);
        gbc.gridy = 0;
        form.add(lblHab, gbc);

        JLabel lblNomHab = new JLabel("Habilidad (Texto):");
        lblNomHab.setFont(fontNegrita);
        lblNomHab.setForeground(colorTexto);
        gbc.gridy = 1;
        form.add(lblNomHab, gbc);

        txtHabilidad = new JTextField();
        styleTextField(txtHabilidad);
        gbc.gridy = 2;
        form.add(txtHabilidad, gbc);

        JLabel lblUserAsoc = new JLabel("Asociar a Usuario:");
        lblUserAsoc.setFont(fontNegrita);
        lblUserAsoc.setForeground(colorTexto);
        gbc.gridy = 3;
        form.add(lblUserAsoc, gbc);

        comboUserHab = new JComboBox();
        styleComboBox(comboUserHab);
        gbc.gridy = 4;
        form.add(comboUserHab, gbc);

        JButton btnHabilidades = new JButton("Agregar Habilidad");
        styleButton(btnHabilidades, colorBoton);
        gbc.gridy = 5;
        form.add(btnHabilidades, gbc);

        JSeparator sep = new JSeparator();
        sep.setForeground(colorBordePanel);
        gbc.gridy = 6;
        form.add(sep, gbc);

        JLabel lblBuscarEsp = new JLabel("Buscar por Especialidad");
        lblBuscarEsp.setFont(fontTitulo);
        lblBuscarEsp.setForeground(colorBoton);
        gbc.gridy = 7;
        form.add(lblBuscarEsp, gbc);

        JLabel lblEsp = new JLabel("Especialidad a buscar:");
        lblEsp.setFont(fontNegrita);
        lblEsp.setForeground(colorTexto);
        gbc.gridy = 8;
        form.add(lblEsp, gbc);

        txtBuscarEspecialidad = new JTextField();
        styleTextField(txtBuscarEspecialidad);
        gbc.gridy = 9;
        form.add(txtBuscarEspecialidad, gbc);

        JButton btnBuscarEsp = new JButton("Buscar Especialidad");
        styleButton(btnBuscarEsp, colorBotonSecundario);
        gbc.gridy = 10;
        form.add(btnBuscarEsp, gbc);

        areaHabilidades = new JTextArea();
        styleTextArea(areaHabilidades);
        JScrollPane scroll = new JScrollPane(areaHabilidades);
        scroll.setBorder(BorderFactory.createLineBorder(colorBordePanel));

        main.add(form, BorderLayout.WEST);
        main.add(scroll, BorderLayout.CENTER);

        btnHabilidades.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgs) {
                String hab = txtHabilidad.getText().trim();
                String item = (String) comboUserHab.getSelectedItem();
                if (hab.isEmpty() || item == null) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Escriba una habilidad y seleccione un usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int userId = Integer.parseInt(item.split(" - ")[0]);
                habilidades.insertar(hab, userId);
                boolean apilado = historial.apilar(new Accion(
                    Accion.Tipo.AGREGAR_HABILIDAD,
                    new Object[]{ hab, userId },
                    "Se insertó la habilidad '" + hab + "' asociada al usuario " + userId + "."
                ));
                if (!apilado) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Advertencia: El historial de acciones está lleno. La acción se completó pero no se podrá deshacer.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
                txtHabilidad.setText("");
                actualizarAreaHabilidades();
                actualizarAreaHistorial();
            }
        });

        btnBuscarEsp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgs) {
                String esp = txtBuscarEspecialidad.getText().trim();
                if (esp.isEmpty()) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Escriba la habilidad a buscar.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String ids = habilidades.buscarPorEspecialidad(esp);
                StringBuilder sb = new StringBuilder();
                sb.append("=== USUARIOS CON ESPECIALIDAD: ").append(esp).append(" ===\n\n");
                if (ids.isEmpty()) {
                    sb.append("(No se encontraron usuarios con esta especialidad)\n");
                } else {
                    String[] parts = ids.split(", ");
                    for (String part : parts) {
                        try {
                            int id = Integer.parseInt(part);
                            Perfil p = perfiles.recuperar(id);
                            if (p != null) {
                                sb.append("- ").append(p.nombre).append(" (ID ").append(id).append(" - ").append(p.profesion).append(")\n");
                            } else {
                                sb.append("- ID: ").append(id).append("\n");
                            }
                        } catch (Exception ex) {
                        }
                    }
                }
                areaHabilidades.setText(sb.toString() + "\n\n" + habilidades.inorden());
            }
        });

        return main;
    }

    private JPanel crearPanelEmpleos() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBackground(colorFondo);
        main.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(colorPanel);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBordePanel),
            new EmptyBorder(15, 15, 15, 15)
        ));
        form.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel lblEmp = new JLabel("Postulaciones de Empleo");
        lblEmp.setFont(fontTitulo);
        lblEmp.setForeground(colorBoton);
        gbc.gridy = 0;
        form.add(lblEmp, gbc);

        JLabel lblPost = new JLabel("Perfil a postular:");
        lblPost.setFont(fontNegrita);
        lblPost.setForeground(colorTexto);
        gbc.gridy = 1;
        form.add(lblPost, gbc);

        comboPostulante = new JComboBox();
        styleComboBox(comboPostulante);
        gbc.gridy = 2;
        form.add(comboPostulante, gbc);

        JButton btnPostular = new JButton("Encolar Postulación");
        styleButton(btnPostular, colorBoton);
        gbc.gridy = 3;
        form.add(btnPostular, gbc);

        JSeparator sep = new JSeparator();
        sep.setForeground(colorBordePanel);
        gbc.gridy = 4;
        form.add(sep, gbc);

        JLabel lblProc = new JLabel("Procesar Fila FIFO");
        lblProc.setFont(fontTitulo);
        lblProc.setForeground(colorBoton);
        gbc.gridy = 5;
        form.add(lblProc, gbc);

        JButton btnProcesar = new JButton("Procesar Siguiente");
        styleButton(btnProcesar, colorBotonSecundario);
        gbc.gridy = 6;
        form.add(btnProcesar, gbc);

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(colorBordePanel);
        gbc.gridy = 7;
        form.add(sep2, gbc);

        JLabel lblIdeal = new JLabel("Candidatos Ideales");
        lblIdeal.setFont(fontTitulo);
        lblIdeal.setForeground(colorBoton);
        gbc.gridy = 8;
        form.add(lblIdeal, gbc);

        JLabel lblReq = new JLabel("Habilidades (Separadas por comas):");
        lblReq.setFont(fontNegrita);
        lblReq.setForeground(colorTexto);
        gbc.gridy = 9;
        form.add(lblReq, gbc);

        txtHabilidadesRequeridas = new JTextField();
        styleTextField(txtHabilidadesRequeridas);
        gbc.gridy = 10;
        form.add(txtHabilidadesRequeridas, gbc);

        JButton btnBuscarIdeales = new JButton("Buscar Candidatos");
        styleButton(btnBuscarIdeales, colorBoton);
        gbc.gridy = 11;
        form.add(btnBuscarIdeales, gbc);

        areaEmpleos = new JTextArea();
        styleTextArea(areaEmpleos);
        JScrollPane scroll = new JScrollPane(areaEmpleos);
        scroll.setBorder(BorderFactory.createLineBorder(colorBordePanel));

        main.add(form, BorderLayout.WEST);
        main.add(scroll, BorderLayout.CENTER);

        btnPostular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgs) {
                String item = (String) comboPostulante.getSelectedItem();
                if (item == null) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Seleccione un perfil.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                boolean encolado = postulaciones.encolar(item);
                if (!encolado) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "No se pudo encolar. La cola de postulaciones está llena.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                boolean apilado = historial.apilar(new Accion(
                    Accion.Tipo.ENCOLAR_POSTULACION,
                    new Object[]{ item },
                    "Se encoló la postulación de " + item + "."
                ));
                if (!apilado) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Advertencia: El historial de acciones está lleno. La acción se completó pero no se podrá deshacer.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
                actualizarAreaEmpleos();
                actualizarAreaHistorial();
            }
        });

        btnProcesar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgs) {
                if (postulaciones.estaVacia()) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "No hay postulaciones en espera.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                String postulado = postulaciones.desencolar();
                boolean apilado = historial.apilar(new Accion(
                    Accion.Tipo.PROCESAR_POSTULACION,
                    new Object[]{ postulado },
                    "Se procesó la postulación de " + postulado + "."
                ));
                if (!apilado) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Advertencia: El historial de acciones está lleno. La acción se completó pero no se podrá deshacer.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
                JOptionPane.showMessageDialog(SistemaRedSocial.this, "Postulación procesada:\n" + postulado, "Procesado Exitoso", JOptionPane.INFORMATION_MESSAGE);
                actualizarAreaEmpleos();
                actualizarAreaHistorial();
            }
        });

        btnBuscarIdeales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgs) {
                String input = txtHabilidadesRequeridas.getText().trim();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Ingrese al menos una habilidad.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String[] habs = input.split(",");
                for (int i = 0; i < habs.length; i++) {
                    habs[i] = habs[i].trim();
                }
                Conjunto candidatos = buscarCandidatosIdeales(habs);
                StringBuilder sb = new StringBuilder();
                sb.append("=== CANDIDATOS IDEALES PARA: ").append(input).append(" ===\n\n");
                if (candidatos == null || candidatos.cantidad == 0) {
                    sb.append("(No se encontraron candidatos con todas las habilidades requeridas)\n");
                } else {
                    for (int i = 0; i < candidatos.cantidad; i++) {
                        int id = candidatos.elementos[i];
                        Perfil p = perfiles.recuperar(id);
                        if (p != null) {
                            sb.append("- ").append(p.nombre).append(" (ID ").append(id).append(" - ").append(p.profesion).append(")\n");
                        } else {
                            sb.append("- ID: ").append(id).append("\n");
                        }
                    }
                }
                areaEmpleos.setText(sb.toString() + "\n\n" + areaEmpleos.getText());
            }
        });

        return main;
    }

    public Conjunto buscarCandidatosIdeales(String[] habilidadesRequeridas) {
        if (habilidadesRequeridas == null || habilidadesRequeridas.length == 0) {
            return new Conjunto();
        }
        Conjunto resultado = habilidades.obtenerUsuariosPorHabilidad(habilidadesRequeridas[0]);
        for (int i = 1; i < habilidadesRequeridas.length; i++) {
            Conjunto temp = habilidades.obtenerUsuariosPorHabilidad(habilidadesRequeridas[i]);
            resultado = resultado.interseccion(temp);
        }
        return resultado;
    }

    private JPanel crearPanelHistorial() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBackground(colorFondo);
        main.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(colorPanel);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBordePanel),
            new EmptyBorder(15, 15, 15, 15)
        ));
        form.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel lblHist = new JLabel("Historial y LIFO");
        lblHist.setFont(fontTitulo);
        lblHist.setForeground(colorBoton);
        gbc.gridy = 0;
        form.add(lblHist, gbc);

        JButton btnDeshacer = new JButton("Deshacer Acción (LIFO)");
        styleButton(btnDeshacer, colorBoton);
        gbc.gridy = 1;
        form.add(btnDeshacer, gbc);

        areaHistorial = new JTextArea();
        styleTextArea(areaHistorial);
        JScrollPane scroll = new JScrollPane(areaHistorial);
        scroll.setBorder(BorderFactory.createLineBorder(colorBordePanel));

        main.add(form, BorderLayout.WEST);
        main.add(scroll, BorderLayout.CENTER);

        btnDeshacer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgs) {
                if (historial.estaVacia()) {
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "No hay acciones para deshacer en la pila.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Accion accion = historial.desapilar();
                if (accion != null) {
                    revertirAccion(accion);
                    JOptionPane.showMessageDialog(SistemaRedSocial.this, "Acción revertida:\n" + accion.descripcion, "Pila LIFO", JOptionPane.INFORMATION_MESSAGE);
                    actualizarAreaPerfiles();
                    actualizarAreaContactos();
                    actualizarAreaHabilidades();
                    actualizarAreaEmpleos();
                    actualizarAreaHistorial();
                    actualizarCombos();
                }
            }
        });

        return main;
    }

    private void revertirAccion(Accion accion) {
        if (accion.tipo == null) {
            return;
        }
        switch (accion.tipo) {
            case REGISTRAR_PERFIL: {
                int id = (Integer) accion.datos[0];
                String habilidad = (String) accion.datos[1];
                perfiles.eliminar(id);
                habilidades.eliminarUsuarioDeHabilidad(habilidad, id);
                for (int i = 0; i < redContactos.numVertices; i++) {
                    redContactos.desconectar(id, i);
                }
                break;
            }
            case CONECTAR_CONTACTOS: {
                int idA = (Integer) accion.datos[0];
                int idB = (Integer) accion.datos[1];
                redContactos.desconectar(idA, idB);
                break;
            }
            case AGREGAR_HABILIDAD: {
                String habilidad = (String) accion.datos[0];
                int id = (Integer) accion.datos[1];
                habilidades.eliminarUsuarioDeHabilidad(habilidad, id);
                break;
            }
            case ENCOLAR_POSTULACION: {
                postulaciones.removerUltimo();
                break;
            }
            case PROCESAR_POSTULACION: {
                String postulado = (String) accion.datos[0];
                postulaciones.encolarFrente(postulado);
                break;
            }
        }
    }

    private void styleTextField(JTextField f) {
        f.setBackground(colorInput);
        f.setForeground(colorTexto);
        f.setCaretColor(colorTexto);
        f.setFont(fontGlobal);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorInputBorder),
            BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));
    }

    private void styleButton(JButton b, Color bg) {
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setFocusPainted(false);
        b.setFont(fontNegrita);
        b.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
    }

    private void styleComboBox(JComboBox c) {
        c.setFont(fontGlobal);
        c.setBackground(Color.WHITE);
        c.setForeground(new Color(33, 37, 41));
    }

    private void styleTextArea(JTextArea a) {
        a.setBackground(colorInput);
        a.setForeground(colorTexto);
        a.setFont(new Font("Monospaced", Font.PLAIN, 12));
        a.setEditable(false);
        a.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void actualizarCombos() {
        comboUserA.removeAllItems();
        comboUserB.removeAllItems();
        comboUserInicio.removeAllItems();
        comboPostulante.removeAllItems();
        comboUserHab.removeAllItems();
        if (comboUserSepA != null) comboUserSepA.removeAllItems();
        if (comboUserSepB != null) comboUserSepB.removeAllItems();

        for (int i = 0; i < perfiles.datos.length; i++) {
            if (perfiles.datos[i] != null && !perfiles.datos[i].borrado) {
                Perfil p = perfiles.datos[i].valor;
                String val = p.id + " - " + p.nombre;
                comboUserA.addItem(val);
                comboUserB.addItem(val);
                comboUserInicio.addItem(val);
                comboPostulante.addItem(val);
                comboUserHab.addItem(val);
                if (comboUserSepA != null) comboUserSepA.addItem(val);
                if (comboUserSepB != null) comboUserSepB.addItem(val);
            }
        }
    }

    private void actualizarAreaPerfiles() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== PERFILES REGISTRADOS ===\n\n");
        int count = 0;
        for (int i = 0; i < perfiles.datos.length; i++) {
            if (perfiles.datos[i] != null && !perfiles.datos[i].borrado) {
                sb.append(perfiles.datos[i].valor.toString()).append("\n");
                count++;
            }
        }
        if (count == 0) {
            sb.append("(No hay perfiles registrados en el Diccionario)\n");
        }
        areaPerfiles.setText(sb.toString());
    }

    private void actualizarAreaContactos() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CONEXIONES EN LA RED ===\n\n");
        int count = 0;
        for (int i = 0; i < redContactos.numVertices; i++) {
            for (int j = i + 1; j < redContactos.numVertices; j++) {
                if (redContactos.matriz[i][j] == 1) {
                    Perfil pA = perfiles.recuperar(i);
                    Perfil pB = perfiles.recuperar(j);
                    String nameA = (pA != null) ? pA.nombre : ("ID " + i);
                    String nameB = (pB != null) ? pB.nombre : ("ID " + j);
                    sb.append(nameA).append(" <---> ").append(nameB).append("\n");
                    count++;
                }
            }
        }
        if (count == 0) {
            sb.append("(No hay conexiones registradas en el Grafo)\n");
        }
        areaContactos.setText(sb.toString());
    }

    private void actualizarAreaHabilidades() {
        String inorden = habilidades.inorden();
        if (inorden.trim().isEmpty()) {
            areaHabilidades.setText("=== JERARQUÍA DE HABILIDADES ===\n\n(No hay habilidades registradas en el Árbol)");
        } else {
            areaHabilidades.setText("=== JERARQUÍA DE HABILIDADES (ORDEN ALFABÉTICO) ===\n\n" + inorden);
        }
    }

    private void actualizarAreaEmpleos() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== COLA DE POSTULACIONES (FIFO) ===\n\n");
        if (postulaciones.cantidad == 0) {
            sb.append("(No hay postulaciones en espera en la Cola)\n");
        } else {
            int idx = postulaciones.frente;
            for (int i = 0; i < postulaciones.cantidad; i++) {
                sb.append((i + 1)).append(". ").append(postulaciones.datos[idx]).append("\n");
                idx = (idx + 1) % postulaciones.max;
            }
        }
        areaEmpleos.setText(sb.toString());
    }

    private void actualizarAreaHistorial() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== HISTORIAL DE ACCIONES (LIFO) ===\n\n");
        if (historial.estaVacia()) {
            sb.append("(No hay acciones en el historial)\n");
        } else {
            for (int i = historial.tope; i >= 0; i--) {
                sb.append("- ").append(historial.datos[i]).append("\n");
            }
        }
        areaHistorial.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SistemaRedSocial().setVisible(true);
            }
        });
    }
}
