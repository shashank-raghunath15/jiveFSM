package edu.buffalo.cse.jive.finiteStateMachine.views;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

/*
 * Program: FiniteStateMachine.java
 * Author: Swaminathan J, Amrita University, India
 * Description: This is a Eclipse plug-in that constructs finite state
 * 				machine given execution trace and key variables. The user
 * 				can load the trace and select the key variables of his
 * 				interest. Rendering of the diagram by PlantUML.
 * Execution: Run As ... Eclipse Application
 */

/*
 * Draw little diagram, when you find an error. Till then disable. 
 */

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import edu.buffalo.cse.jive.finiteStateMachine.models.KeyAttribute;
import edu.buffalo.cse.jive.finiteStateMachine.models.RuntimeMonitor;
import edu.buffalo.cse.jive.finiteStateMachine.models.State;
import edu.buffalo.cse.jive.finiteStateMachine.models.StateDiagram;

public class FiniteStateMachine extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "edu.buffalo.cse.jive.finiteStateMachine.views.FiniteStateMachine";
	private IStatusLineManager statusLineManager;
	private Display display;
	private ScrolledComposite rootScrollComposite;
	private Composite mainComposite;
	private Label fileLabel;
	private Text fileText;
	private Combo attributeList;
	private Button browseButton;
	private Button listenButton;
	private Button stopButton;
	private Button exportButton;
	Composite imageComposite;
	Composite image2Composite;
	private Image image;
	public boolean horizontal;
	public boolean vertical;

	private Label kvLabel;
	private Text kvText;
	private Label paLabel; // For predicate abstraction
	private Text paText; // For predicate abstraction
	private Button addButton;
	private Button resetButton;
	private Button drawButton;
	private Button ssChkBox; // For step-by-step construction
	private Button startButton;
	private Button prevButton;
	private Button nextButton;
	private Button endButton;
	private Button startButton2;
	private Button prevButton2;
	private Button nextButton2;
	private Button endButton2;
	private Button[] granularity;
	private Label grLabel;
	private Label repeatLabel;
	private Text repeatText;
	private Button transitionCount;
	private Button aRun;

	StateDiagram sd;
	private Label kvSyntax;
	private Label kvSpace;
	private Label paSpace; // For predicate abstraction
	private Label paSyntax; // For predicate abstraction
	private int count;

	Browser browser; // For svg support
	private Label canvasLabel;
	private Label byLabel;
	private Label statusLabel;
	private Text hcanvasText;
	private Text vcanvasText;
	String svg;
	private Label propertyLabel;
	private Text propertyText;

	/**
	 * The constructor.
	 */
	public FiniteStateMachine() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {

		statusLineManager = getViewSite().getActionBars().getStatusLineManager();
		display = parent.getDisplay();

		GridLayout layoutParent = new GridLayout();
		layoutParent.numColumns = 1;
		parent.setLayout(layoutParent);

		rootScrollComposite = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		rootScrollComposite.setLayout(new GridLayout(1, false));
		rootScrollComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		rootScrollComposite.setExpandHorizontal(true);
		rootScrollComposite.setExpandVertical(true);

		mainComposite = new Composite(rootScrollComposite, SWT.NONE);
		rootScrollComposite.setContent(mainComposite);

		mainComposite.setLayout(new GridLayout(1, false));
		mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite browseComposite = new Composite(mainComposite, SWT.NONE);
		browseComposite.setLayout(new GridLayout(5, false));
		browseComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		listenButton = new Button(browseComposite, SWT.PUSH);
		listenButton.setText("Listen");
		stopButton = new Button(browseComposite, SWT.PUSH);
		stopButton.setText("Stop");
		browseButton = new Button(browseComposite, SWT.PUSH);
		browseButton.setText("Browse");

		fileLabel = new Label(browseComposite, SWT.FILL);
		fileLabel.setText("CSV File : ");

		fileText = new Text(browseComposite, SWT.READ_ONLY);
		fileText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
		GridData gd = new GridData();
		gd.widthHint = 550;
		fileText.setLayoutData(gd);

		// Key Attributes Composite

		Composite kvComposite = new Composite(mainComposite, SWT.NONE);
		kvComposite.setLayout(new GridLayout(2, false));
		kvComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		kvLabel = new Label(kvComposite, SWT.FILL);
		kvLabel.setText("Key Attributes");

		kvText = new Text(kvComposite, SWT.BORDER | SWT.FILL);
		GridData gd5 = new GridData();
		gd5.widthHint = 650;
		kvText.setLayoutData(gd5);

		kvSpace = new Label(kvComposite, SWT.FILL);
		kvSpace.setText("                     ");

		kvSyntax = new Label(kvComposite, SWT.FILL);
		kvSyntax.setText("   class:index->field,......,class:index->field");

		// Choice composite
		Composite evComposite = new Composite(mainComposite, SWT.NONE);
		evComposite.setLayout(new GridLayout(10, false));
		evComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		// attributeList = new Combo(evComposite, SWT.SIMPLE | SWT.BORDER);
		attributeList = new Combo(evComposite, SWT.DROP_DOWN | SWT.BORDER);

		addButton = new Button(evComposite, SWT.PUSH);
		addButton.setText("Add");
		addButton.setToolTipText("Adds the key attribute selected");

		resetButton = new Button(evComposite, SWT.PUSH);
		resetButton.setText("Reset");
		resetButton.setToolTipText("Clears the key attributes");

		drawButton = new Button(evComposite, SWT.PUSH);
		drawButton.setText("Draw");
		drawButton.setToolTipText("Draws the state diagram");

		exportButton = new Button(evComposite, SWT.PUSH);
		exportButton.setText("Export");
		exportButton.setToolTipText("Exports the state diagram");

		ssChkBox = new Button(evComposite, SWT.CHECK);
		ssChkBox.setSelection(false);
		ssChkBox.setText("Step-by-step");

		startButton = new Button(evComposite, SWT.PUSH);
		startButton.setText("Start");
		startButton.setToolTipText("Start state");
		startButton.setEnabled(false);

		prevButton = new Button(evComposite, SWT.PUSH);
		prevButton.setText("Prev");
		prevButton.setToolTipText("Previous state");
		prevButton.setEnabled(false);

		nextButton = new Button(evComposite, SWT.PUSH);
		nextButton.setText("Next");
		nextButton.setToolTipText("Next state");
		nextButton.setEnabled(false);

		endButton = new Button(evComposite, SWT.PUSH);
		endButton.setText(" End ");
		endButton.setToolTipText("End state");
		endButton.setEnabled(false);

		// Granularity composite
		Composite grComposite = new Composite(mainComposite, SWT.NONE);
		grComposite.setLayout(new GridLayout(10, false));
		grComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		grLabel = new Label(grComposite, SWT.FILL);
		grLabel.setText("Granularity");

		granularity = new Button[2];

		granularity[0] = new Button(grComposite, SWT.RADIO);
		granularity[0].setSelection(true);
		granularity[0].setText("Field");

		granularity[1] = new Button(grComposite, SWT.RADIO);
		granularity[1].setSelection(false);
		granularity[1].setText("Method");

		repeatLabel = new Label(grComposite, SWT.FILL);
		repeatLabel.setText("       Mininmum Field Updates");

		repeatText = new Text(grComposite, SWT.BORDER | SWT.FILL);
		GridData gd6 = new GridData();
		gd6.widthHint = 25;
		repeatText.setLayoutData(gd6);
		repeatText.setText("1");

		transitionCount = new Button(grComposite, SWT.CHECK);
		transitionCount.setSelection(true);
		transitionCount.setText("Count transitions");

		aRun = new Button(grComposite, SWT.CHECK);
		aRun.setSelection(false);
		aRun.setText("Run");

		// Predicate abstraction composite
		Composite paComposite = new Composite(mainComposite, SWT.NONE);
		paComposite.setLayout(new GridLayout(2, false));
		paComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		paLabel = new Label(paComposite, SWT.FILL);
		paLabel.setText("Abstraction");

		paText = new Text(paComposite, SWT.BORDER | SWT.FILL);
		GridData gd5b = new GridData();
		gd5b.widthHint = 400;
		paText.setLayoutData(gd5b);

		paSpace = new Label(paComposite, SWT.FILL);
		paSpace.setText("                     ");

		paSyntax = new Label(paComposite, SWT.FILL);
		paSyntax.setText("Comma-separated entries each of which may be =n, <n, >n, #n, \n"
				+ "[a:b:..:c] or left empty, e.g., =5,,>3,[2:5:8],#true,<4.17,=str");
		// Predicate abstraction changes end
		Composite grammarView = new Composite(mainComposite, SWT.NONE);
		grammarView.setLayout(new GridLayout(3, false));
		grammarView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		propertyLabel = new Label(grammarView, SWT.FILL);
		propertyLabel.setText("Properties       ");

		propertyText = new Text(grammarView, SWT.V_SCROLL);
		GridData grid = new GridData();
		grid.widthHint = 400;
		grid.heightHint = 100;
		propertyText.setLayoutData(grid);
		// Image composite

		imageComposite = new Composite(mainComposite, SWT.NONE);
		imageComposite.setLayout(new GridLayout(1, false));
		imageComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// imageLabel = new Label(imageComposite,SWT.NONE);
		browser = new Browser(imageComposite, SWT.NONE);
		// canvas = new Canvas(imageComposite,SWT.NO_REDRAW_RESIZE);
		// rootScrollComposite.setMinSize(mainComposite.computeSize(SWT.DEFAULT,
		// SWT.DEFAULT));

		// Ev2 composite
		Composite ev2Composite = new Composite(mainComposite, SWT.NONE);
		ev2Composite.setLayout(new GridLayout(8, false));
		ev2Composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		startButton2 = new Button(ev2Composite, SWT.PUSH);
		startButton2.setText("Start");
		startButton2.setToolTipText("Start state");
		startButton2.setEnabled(false);

		prevButton2 = new Button(ev2Composite, SWT.PUSH);
		prevButton2.setText("Prev");
		prevButton2.setToolTipText("Previous state");
		prevButton2.setEnabled(false);

		nextButton2 = new Button(ev2Composite, SWT.PUSH);
		nextButton2.setText("Next");
		nextButton2.setToolTipText("Next state");
		nextButton2.setEnabled(false);

		endButton2 = new Button(ev2Composite, SWT.PUSH);
		endButton2.setText(" End ");
		endButton2.setToolTipText("End state");
		endButton2.setEnabled(false);

		// Added for SVG support test

		canvasLabel = new Label(ev2Composite, SWT.FILL);
		canvasLabel.setText("Canvas dimension");

		hcanvasText = new Text(ev2Composite, SWT.BORDER | SWT.FILL);
		GridData hcd = new GridData();
		hcd.widthHint = 40;
		hcanvasText.setLayoutData(hcd);
		hcanvasText.setText("1000");

		byLabel = new Label(ev2Composite, SWT.FILL);
		byLabel.setText("   X    ");

		vcanvasText = new Text(ev2Composite, SWT.BORDER | SWT.FILL);
		GridData vcd = new GridData();
		vcd.widthHint = 40;
		vcanvasText.setLayoutData(vcd);
		vcanvasText.setText("600");

		statusLabel = new Label(ev2Composite, SWT.FILL);
		statusLabel.setText("StatusUpdate:");

		rootScrollComposite.setMinSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		listenButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				listenButtonAction(e);
			}
		});

		browseButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				browseButtonAction(e);
			}
		});

		exportButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				exportButtonAction(e);
			}
		});

		attributeList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				keyAttributeAction(e);
			}
		});

		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addButtonAction(e);
			}
		});

		drawButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				drawButtonAction(e);
			}
		});

		resetButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				resetButtonAction(e);
			}
		});

		ssChkBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ssChkBoxAction(e);
			}
		});

		startButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				startButtonAction(e);
			}
		});

		prevButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				prevButtonAction(e);
			}
		});

		nextButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				nextButtonAction(e);
			}
		});

		endButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				endButtonAction(e);
			}
		});

		startButton2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				startButtonAction(e);
			}
		});

		prevButton2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				prevButtonAction(e);
			}
		});

		nextButton2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				nextButtonAction(e);
			}
		});

		endButton2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				endButtonAction(e);
			}
		});
		stopButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Job.getJobManager().cancel("MonitorPortJob");
			}
		});
	}

	private void listenButtonAction(SelectionEvent e) {
		// Try 5 - Using JobsAPI

		RuntimeMonitor rm = new RuntimeMonitor(kvText, statusLineManager, propertyText, display, statusLabel);
		Job job = new Job("MonitorPortJob") {
			ServerSocket server;
			Socket socket;

			protected IStatus run(IProgressMonitor monitor) {
				String line = "";
				try {
					server = new ServerSocket(5000);
					System.out.println("Server started at port 5000");

					socket = server.accept();
					System.out.println("Client accepted");
					DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream(), 131072));
					while (true) {
						if (in.available() > 0) {
							try {
								line = in.readUTF();
								if (line.contains("System End"))
									break;
								line = line.replace("\"", "").trim();
								if (rm.recordFieldWrite(line.trim()))
									rm.generateSVG(rm.exportToPlantUML(true), hcanvasText, vcanvasText, browser,
											imageComposite, rootScrollComposite, mainComposite);
							} catch (IOException i) {
								i.printStackTrace();
								break;
							} catch (StringIndexOutOfBoundsException e2) {
								e2.printStackTrace();
							}

						}
					}
					socket.close();
					server.close();
					updateUI("Socket: Client Disconnected");

				} catch (EOFException eofe) {
					System.out.println(line);
					updateUI("Error:Reached end-of-file");
					System.out.println("Reached end-of-file");
				} catch (IOException ioe) {
					System.out.println("Connection problem");
					updateUI("Error:Connection problem");
					System.out.println(line);
				}
				return Status.OK_STATUS;
			}

			@Override
			protected void canceling() {
				super.canceling();
				try {
					System.out.println("Server Closed");
					socket.close();
					server.close();
					rm.kill();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public boolean belongsTo(Object family) {
				return family.equals("MonitorPortJob");
			}
		};
		job.setUser(true);
		job.schedule();
	}

	void updateUI(String message) {
		display.asyncExec(new Runnable() {

			@Override
			public void run() {
				statusLabel.setText(message);
			}
		});
	}

	private void browseButtonAction(SelectionEvent e) {

		if (image != null) {
			if (!image.isDisposed()) {
				System.out.println("Image disposedx");
				image.dispose();
			}
		}

		statusLineManager.setMessage(null);

		FileDialog fd = new FileDialog(new Shell(Display.getCurrent(), SWT.OPEN));
		fd.setText("Open CSV File");

		String[] filterExtensions = { "*.csv" };
		fd.setFilterExtensions(filterExtensions);

		String fileName = fd.open();
		if (fileName == null)
			return;
		fileText.setText(fileName);

		sd = new StateDiagram();
		attributeList.removeAll(); // Reset dropdown box
		kvText.setText("");
		paText.setText("");
		drawButton.setEnabled(true);
		ssChkBox.setSelection(false);
		aRun.setSelection(false);

		sd.setTraceFile(fileName); // Get the file name
		sd.readEvents(Integer.MAX_VALUE, repeatText, attributeList, granularity);
		sd.printAttributes();

		statusLineManager.setMessage("Loaded " + fileName);
	}

	private void exportButtonAction(SelectionEvent e) {

		IPath path = ResourcesPlugin.getPlugin().getStateLocation();
		String from = path.toFile().getPath() + File.separator + "state.svg";

		FileDialog fd = new FileDialog(new Shell(Display.getCurrent()), SWT.SAVE);
		fd.setText("Export As");
		// String[] filterExtensions = {"*.png", "*.bmp", "*.jpg"};
		String[] filterExtensions = { "*.svg" };
		fd.setFilterExtensions(filterExtensions);

		String to = fd.open();
		if (to == null)
			return;
		System.out.println("From " + from + " to" + to);

		File fromFile = new File(from);
		File toFile = new File(to);
		try {
			Files.copy(fromFile.toPath(), toFile.toPath());
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	private void keyAttributeAction(SelectionEvent e) {
		String keyVar = attributeList.getText();
		System.out.println(keyVar);
		statusLineManager.setMessage("Selected key attribute: " + keyVar);
	}

	private void addButtonAction(SelectionEvent e) {

		String keyVar = attributeList.getText();
		if (!keyVar.equals("")) {
			if (kvText.getText().equals(""))
				kvText.setText(keyVar);
			else
				kvText.setText(kvText.getText() + "," + keyVar);
			System.out.println("Adding key attribute ... " + keyVar);
		}
		attributeList.setText("");
	}

	private void drawButtonAction(SelectionEvent e) {
		sd.setKeys(new ArrayList<KeyAttribute>()); // Reallocate key attributes
		sd.setStates(new ArrayList<State>());
		sd.setPaStates(new ArrayList<State>());
		sd.setTransitions(new LinkedHashMap<String, Integer>()); // Reset transitions

		if (kvText.getText().equals(""))
			return;

		sd.readKeyAttributes(kvText, statusLineManager);

		sd.createStates();
		if (granularity[1].getSelection()) // If method granularity
			sd.methodConsolidation();

		sd.abstraction(paText);

		if (aRun.getSelection()) {
			// sd.generateImage(sd.exportRun(Integer.MAX_VALUE));
			sd.generateSVG(sd.exportRun(Integer.MAX_VALUE), browser, hcanvasText, vcanvasText, rootScrollComposite,
					mainComposite, imageComposite);
			// sd.displayImage();
			statusLineManager.setMessage("A run for " + kvText.getText());
		} else {
			sd.createTransitions(Integer.MAX_VALUE);
			if (transitionCount.getSelection()) {
				// sd.generateImage(sd.exportToPlantUML(true));
				sd.generateSVG(sd.exportToPlantUML(true), browser, hcanvasText, vcanvasText, rootScrollComposite,
						mainComposite, imageComposite);
			} else {
				// sd.generateImage(sd.exportToPlantUML(false));
				sd.generateSVG(sd.exportToPlantUML(false), browser, hcanvasText, vcanvasText, rootScrollComposite,
						mainComposite, imageComposite);
			}
			// sd.displayImage();

			statusLineManager.setMessage("Finite State Model for " + kvText.getText());
		}
	}

	private void resetButtonAction(SelectionEvent e) {
		kvText.setText("");
		paText.setText("");
	}

	private void ssChkBoxAction(SelectionEvent e) {
		if (ssChkBox.getSelection()) {

			drawButton.setEnabled(false);
			prevButton.setEnabled(false);
			prevButton2.setEnabled(false);
			startButton.setEnabled(true);
			startButton2.setEnabled(true);
		} else {
			drawButton.setEnabled(true);
			startButton.setEnabled(false);
			startButton2.setEnabled(false);
			prevButton.setEnabled(false);
			prevButton2.setEnabled(false);
			nextButton.setEnabled(false);
			nextButton2.setEnabled(false);
			endButton.setEnabled(false);
			endButton2.setEnabled(false);
		}
	}

	private void startButtonAction(SelectionEvent e) {

		sd.setKeys(new ArrayList<KeyAttribute>()); // Reallocate key attributes
		sd.setStates(new ArrayList<State>());
		sd.setPaStates(new ArrayList<State>());
		sd.setTransitions(new LinkedHashMap<String, Integer>()); // Reset transitions
		sd.setAllTransitions(new ArrayList<String>());
		sd.readKeyAttributes(kvText, statusLineManager);
		sd.printKeyAttributes();

		sd.createStates();
		if (granularity[1].getSelection()) // If method granularity
			sd.methodConsolidation();

		sd.abstraction(paText);

		count = 0;
		if (aRun.getSelection()) {
			// sd.generateImage(sd.exportRun(count));
			sd.generateSVG(sd.exportRun(count), browser, hcanvasText, vcanvasText, rootScrollComposite, mainComposite,
					imageComposite);
			// sd.displayImage();
			statusLineManager.setMessage("A partial run for " + kvText.getText());
		} else {
			sd.createTransitions(count);
			if (transitionCount.getSelection()) {
				// sd.generateImage(sd.exportToPlantUML(true));
				sd.generateSVG(sd.exportToPlantUML(true), browser, hcanvasText, vcanvasText, rootScrollComposite,
						mainComposite, imageComposite);
			} else {
				// sd.generateImage(sd.exportToPlantUML(false));
				sd.generateSVG(sd.exportToPlantUML(false), browser, hcanvasText, vcanvasText, rootScrollComposite,
						mainComposite, imageComposite);
			}
			// sd.displayImage();

			statusLineManager.setMessage("Partial State Diagram for " + kvText.getText());
		}

		prevButton.setEnabled(false);
		prevButton2.setEnabled(false);
		nextButton.setEnabled(true);
		nextButton2.setEnabled(true);
		endButton.setEnabled(true);
		endButton2.setEnabled(true);
	}

	private void prevButtonAction(SelectionEvent e) {
		if (count > 0) {
			nextButton.setEnabled(true);
			nextButton2.setEnabled(true);
		}

		if (aRun.getSelection()) {
			// sd.generateImage(sd.exportRun(--count));
			sd.generateSVG(sd.exportRun(--count), browser, hcanvasText, vcanvasText, rootScrollComposite, mainComposite,
					imageComposite);
			// sd.displayImage();
			statusLineManager.setMessage("A partial run for " + kvText.getText());
		} else {
			sd.createTransitions(--count);
			if (transitionCount.getSelection()) {
				// sd.generateImage(sd.exportToPlantUML(true));
				sd.generateSVG(sd.exportToPlantUML(true), browser, hcanvasText, vcanvasText, rootScrollComposite,
						mainComposite, imageComposite);
			} else {
				// sd.generateImage(sd.exportToPlantUML(false));
				sd.generateSVG(sd.exportToPlantUML(false), browser, hcanvasText, vcanvasText, rootScrollComposite,
						mainComposite, imageComposite);
			}
			// sd.displayImage();

			statusLineManager.setMessage("Partial State Diagram for " + kvText.getText());
		}

		if (count <= 0) {
			prevButton.setEnabled(false);
			prevButton2.setEnabled(false);
		}
	}

	private void nextButtonAction(SelectionEvent e) {
		if (count < sd.getPaStates().size() - 1) {
			prevButton.setEnabled(true);
			prevButton2.setEnabled(true);
		}

		if (aRun.getSelection()) {
			// sd.generateImage(sd.exportRun(++count));
			sd.generateSVG(sd.exportRun(++count), browser, hcanvasText, vcanvasText, rootScrollComposite, mainComposite,
					imageComposite);
			// sd.displayImage();
			statusLineManager.setMessage("A partial run for " + kvText.getText());
		} else {
			sd.createTransitions(++count);
			if (transitionCount.getSelection()) {
				// sd.generateImage(sd.exportToPlantUML(true));
				sd.generateSVG(sd.exportToPlantUML(true), browser, hcanvasText, vcanvasText, rootScrollComposite,
						mainComposite, imageComposite);
			} else {
				sd.generateSVG(sd.exportToPlantUML(false), browser, hcanvasText, vcanvasText, rootScrollComposite,
						mainComposite, imageComposite);
				// sd.generateImage(sd.exportToPlantUML(false));
			}
			// sd.displayImage();

			statusLineManager.setMessage("Partial State Diagram for " + kvText.getText());
		}

		if (count >= sd.getPaStates().size() - 1) {
			nextButton.setEnabled(false);
			nextButton2.setEnabled(false);
		}
	}

	private void endButtonAction(SelectionEvent e) {
		count = sd.getPaStates().size() - 1;

		if (aRun.getSelection()) {
			// sd.generateImage(sd.exportRun(Integer.MAX_VALUE));
			sd.generateSVG(sd.exportRun(Integer.MAX_VALUE), browser, hcanvasText, vcanvasText, rootScrollComposite,
					mainComposite, imageComposite);
			// sd.displayImage();
			statusLineManager.setMessage("A partial run for " + kvText.getText());
		} else {
			sd.createTransitions(Integer.MAX_VALUE);
			if (transitionCount.getSelection()) {
				// sd.generateImage(sd.exportToPlantUML(true));
				sd.generateSVG(sd.exportToPlantUML(true), browser, hcanvasText, vcanvasText, rootScrollComposite,
						mainComposite, imageComposite);
			} else {
				sd.generateSVG(sd.exportToPlantUML(false), browser, hcanvasText, vcanvasText, rootScrollComposite,
						mainComposite, imageComposite);
				// sd.generateImage(sd.exportToPlantUML(false));
			}
			// sd.displayImage();

			statusLineManager.setMessage("Partial State Diagram for " + kvText.getText());
		}

		prevButton.setEnabled(true);
		prevButton2.setEnabled(true);
		nextButton.setEnabled(false);
		nextButton2.setEnabled(false);
	}

	public void setFocus() {
		// viewer.getControl().setFocus();
	}
}