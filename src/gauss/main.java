 package gauss;

import ui.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.PlainDocument; 

public class main {

	
	
	public static String querryCard = "querry";
	public static String processCard = "process";
	public static Integer X = 0;
	public static Integer Y = 0;
	public static Integer nf = 0;
	public static Boolean flag = false;
	public static DefaultTableModel mdl = new DefaultTableModel();
	public static JButton step;

	static Map<Dimension, Double> matrix = new HashMap<Dimension, Double>();
	static Map<Integer, Double> answers = new HashMap<Integer, Double>();
	@SuppressWarnings("unused")
	private static String[] args;
	
	public static void main(String[] args) {

		
		SimpleUI ui = new SimpleUI(250, 500, "Гаусс");
		
		ui.createPanel(250, 300, querryCard);
		ui.createPanel(250, 300, processCard);
		ui.setLayout(querryCard, LayoutConstants.BORDER_LAYOUT);
		ui.setLayout(processCard, LayoutConstants.BORDER_LAYOUT);
		ui.setResizable(false);

		
		
		generateListener(ui);
		
		
		ui.setLocationRelativeTo(null);
		ui.pack();
		ui.setDefaultLookAndFeelDecorated(true);
		ui.setVisible(true);
		
	}
	
	public static void generateListener(SimpleUI ui) {
		ui.setPanel(querryCard);
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(new JLabel("Введите X"));
		contentPane.add(Box.createRigidArea(new Dimension(0,1)));
		//ui.addLabel("", querryCard);
		JTextField XArea = new JTextField();
		PlainDocument docX = (PlainDocument) XArea.getDocument();
		docX.setDocumentFilter(new MyIntFilter());
		
		contentPane.add(XArea);
		contentPane.add(new JLabel("Введите Y"));
		contentPane.add(Box.createRigidArea(new Dimension(0,1)));
		//ui.addLabel("", querryCard);
		JTextField YArea = new JTextField();

		PlainDocument docY = (PlainDocument) YArea.getDocument();
		docY.setDocumentFilter(new MyIntFilter());
		
		contentPane.add(YArea);
		
		JPanel btnPane = new JPanel();
		btnPane.setLayout(new BoxLayout(btnPane, BoxLayout.LINE_AXIS));
		btnPane.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		JButton btn = new JButton("Сгенерировать");
		btnPane.add(Box.createHorizontalGlue());
		btnPane.add(btn);
		JButton btnMake = new JButton("Задать");
		btnPane.add(Box.createHorizontalGlue());
		btnPane.add(btnMake);
		//btn.setPreferredSize(new Dimension(250, 50));
		
		ui.getPanel(querryCard).add(contentPane, BorderLayout.NORTH);
		ui.getPanel(querryCard).add(btnPane , BorderLayout.SOUTH);
		
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("X: "+XArea.getText()+"\nY: "+YArea.getText());
				generateProcess(Integer.parseInt(XArea.getText()), Integer.parseInt(YArea.getText()), ui, true);
			}});
		btnMake.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("X: "+XArea.getText()+"\nY: "+YArea.getText());
				generateProcess(Integer.parseInt(XArea.getText()), Integer.parseInt(YArea.getText()), ui, false);
			}});
	}
	
	public static void generateProcess(int x, int y, SimpleUI ui, boolean generate) {
		
			X = x+1;
			Y = y;
			for (int i = 0; i < y; i++) {
				for (int j = 0; j < x; j++) {
					if(generate) {
						matrix.put(new Dimension(j,i), (double) (new Random().nextInt(5 + 5) - 5));
					} else {
						matrix.put(new Dimension(j,i), (double) 0);
					}
				}
				if(generate) {
					BigDecimal scaled = BigDecimal.valueOf(new Random().nextDouble()*10);
					scaled = scaled.round(new MathContext(3));
					matrix.put(new Dimension(X-1,i), (double) (new Random().nextInt(20)));
				} else {
					matrix.put(new Dimension(X-1,i), (double) 0);
				}
			}
			
			ui.getPanel(processCard).setMinimumSize(new Dimension(300,500));
			
			Vector<String> ids = new Vector<String>();
			for (int i = 0; i < X; i++) {
				ids.add("Col "+(i+1));
			}
			
			
			JTable tbl = new JTable();
			
			mdl.setColumnIdentifiers(ids);
			if(generate) {
				tbl.setDefaultEditor(Object.class, null);
			} 
			tbl.setModel(mdl);
			tbl.setRowHeight(30);
			tbl.getTableHeader().setReorderingAllowed(false);
			TableColumnModel cModel = tbl.getColumnModel();
			for (int i = 0; i < tbl.getColumnCount(); i++) {
				
				cModel.getColumn(i).setMaxWidth(30);
				cModel.getColumn(i).setMinWidth(30);
			}
			
			JScrollPane sPane = new JScrollPane(tbl, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			sPane.setWheelScrollingEnabled(true);
			sPane.setEnabled(false);
			sPane.setPreferredSize(new Dimension(250, 250));
			
			tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			
			ui.createPanel(500, 500, "table");
			ui.getPanel("table").setLayout(new FlowLayout());
			//ui.getPanel("table").add(tbl);
			
			ui.getPanel(processCard).add(sPane, BorderLayout.NORTH);
			if(!generate) {
				step = ui.addButton("Готово", processCard);
			} else {
				step = ui.addButton("Следующий шаг", processCard);
			}
			ui.getPanel(processCard).remove(step);
			ui.getPanel(processCard).add(step, BorderLayout.SOUTH);
			if(generate) {
				step.addActionListener(new ActionListener() {
		
					@Override
					public void actionPerformed(ActionEvent e) {
						step.setEnabled(false);
						if(!flag) {
							iterate(step);	
						} else {
							iterate(step);
							JPanel lPane = new JPanel();
							JScrollPane sPane = new JScrollPane(lPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
							sPane.setWheelScrollingEnabled(true);
							lPane.setLayout(new BoxLayout(lPane, BoxLayout.Y_AXIS));
							ui.getPanel(processCard).add(sPane, BorderLayout.CENTER);
							for (int i = 1; i < answers.size()+1; i++) {
								JLabel lbl = new JLabel("Var #" + (i) + " = " + answers.get(i));
								lPane.add(lbl);
								lPane.add(Box.createRigidArea(new Dimension(10,10)));
							}
							ui.pack();
						}
						matrixDisplay();				
					}
					
				});
			} else {
				step.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						for (int i = 0; i < y; i++) {
							for (int j = 0; j < X; j++) {
								matrix.put(new Dimension(j, i), Double.valueOf(tbl.getModel().getValueAt(i, j).toString()));
							}
						}
						tbl.setDefaultEditor(Object.class, null);
						step.setText("Следующий шаг");
						step.removeActionListener(this);
						step.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								step.setEnabled(false);
								if(!flag) {
									iterate(step);	
								} else {
									iterate(step);
									JPanel lPane = new JPanel();
									JScrollPane sPane = new JScrollPane(lPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
									sPane.setWheelScrollingEnabled(true);
									lPane.setLayout(new BoxLayout(lPane, BoxLayout.Y_AXIS));
									ui.getPanel(processCard).add(sPane, BorderLayout.CENTER);
									for (int i = 1; i < answers.size()+1; i++) {
										JLabel lbl = new JLabel("Var #" + (i) + " = " + answers.get(i));
										lPane.add(lbl);
										lPane.add(Box.createRigidArea(new Dimension(10,10)));
									}
									ui.pack();
								}
								matrixDisplay();				
							}
							
						});
					}
				});
			}
			
			matrixDisplay();
			
			ui.setPanel(processCard);
			ui.pack();
	}


	public static void iterate(JButton step) {

		if(matrix.get(new Dimension(0,0)) != 1) {
			if(matrix.get(new Dimension(0, 0)) == 0.0) {
				matrixSwap(0, matrixFind(1, 0, false));
			}
			for (int i = 1; i < Y; i++) {
				if (matrix.get(new Dimension(0, i)) == 1 ){
					matrixSwap(0, i);
					break;
				}
			}
			if(matrix.get(new Dimension(0,0)) != 1) {
				matrixMult(0, matrix.get(new Dimension(0,0)), true);
			};
			step.setEnabled(true);
			return;
		}
		
		if (!flag) {
			for (int i = nf+1; i < Y; i++) {
				if(matrix.get(new Dimension(nf,nf)) == 0) {
					//System.out.println("Swap");
					matrixSwap(nf, matrixFind(nf, nf, false));
				}

				//System.out.println("Y = " + i);
				BigDecimal k = BigDecimal.valueOf(matrix.get(new Dimension(nf,nf)));
				
				//System.out.println(k + " | " + matrix.get(new Dimension(nf, i)));
				
				k = BigDecimal.valueOf(matrix.get(new Dimension(nf, i))).divide(k,new MathContext(3, RoundingMode.HALF_EVEN));
				
				//System.out.println(k);
				
				for (int j = nf; j < X; j++) {
					//System.out.println("X = " + j);
					BigDecimal first = BigDecimal.valueOf(matrix.get(new Dimension(j,nf)));
					first = first.multiply(k, new MathContext(2));
					//System.out.println(matrix.get(new Dimension(j,nf)) + " => " + first);
					BigDecimal next = BigDecimal.valueOf(matrix.get(new Dimension(j, i)));
					next = next.subtract(first, new MathContext(2));
					//System.out.println(matrix.get(new Dimension(j, i)) + " => " + next);
					matrix.put(new Dimension(j, i), next.doubleValue());
				}
			}
			nf++;
			if(nf >= Y-1) {		
				flag = true;
			} 
			step.setEnabled(true);
		} else {
			for (int i = 0; i < nf; i++) {
				answers.put((i+1), null);
			}
			for (int i = Y-1; i >= 0; i--) {
				BigDecimal total = BigDecimal.valueOf(0.0);
				for (int j = nf; j >= 0; j--) {
					BigDecimal current = BigDecimal.valueOf(matrix.get(new Dimension(j,i)));
					int var = Math.abs(j+1);
					if(answers.get(var) == null && (current.compareTo(BigDecimal.valueOf(0)) != 0)) {
						BigDecimal result = BigDecimal.valueOf(matrix.get(new Dimension(X-1,i)));
						result = result.subtract(total, new MathContext(3));
						result = result.divide(current, new MathContext(3));
						result = result.round(new MathContext(2));
						answers.put(var, result.doubleValue());
						//System.out.println(var + " = " + current + " : " + answers.get(var));
					} else if (current.compareTo(BigDecimal.valueOf(0)) != 0) {
						total = total.add(current.multiply(BigDecimal.valueOf(answers.get(var)), new MathContext(3)));
						total = total.round(new MathContext(2));						
						//System.out.println(var + " = " + current + " : " + total);
					}
				}
				//System.out.println();
			}
			System.out.print(answers);
		}
		
	}
	
	public static void matrixSwap(int start, int end) {
		if (end == 999) return;
		for (int j = 0; j < X; j++) {
			double buffer = matrix.get(new Dimension(j, start));
			matrix.put(new Dimension(j, start), matrix.get(new Dimension(j, end)));
			matrix.put(new Dimension(j, end), buffer);
		}
	}
	
	public static void matrixDisplay() {
		mdl.setRowCount(0);
		for (int i = 0; i < Y; i++) {
			Vector<Double> res = new Vector<Double>();
			for (int j = 0; j < X; j++) {
				res.add(matrix.get(new Dimension(j,i)));
			}
			mdl.addRow(res);
			System.out.print("\n");			
		}
	}
	
	public static int matrixFind(int start, int x, boolean zero) {
		for (int i = start; i < Y; i++) {
			if (matrix.get(new Dimension(x,i)) == 0 && zero) {
				return i;
			} else if(matrix.get(new Dimension(x,i)) != 0 && !zero) {
				return i;
			}
		}
		return 999;
	}
	
	public static void matrixMult(int y, double k, boolean div) {
		for (int i = 0; i < X; i++) {
			BigDecimal scaled = BigDecimal.valueOf(matrix.get(new Dimension(i, y)));
			if(div) {
				scaled = scaled.divide(BigDecimal.valueOf(k), new MathContext(3));				
			} else {
				scaled = scaled.multiply(BigDecimal.valueOf(k), new MathContext(3));				
			}
			matrix.put(new Dimension(i, y), scaled.doubleValue());
		}
	}
}
