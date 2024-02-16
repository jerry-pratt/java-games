package rocks.jerryPratt.javaGames.tdGammon;

public class TDGammonNeuralNetwork 
{
	private double alpha, lambda, gamma;
	
   private ActivationFunction firstLayerActivationFunction;
   private ActivationFunction secondLayerActivationFunction;
	
	private final int inputUnits;
	private final int hiddenUnits;
	
	private final double[][] firstLayerWeights;
	private final double[] secondLayerWeights;
	private final double[] firstLayerBiases; 

	private final double[] firstLayerBeforeSigmoidDerivatives;
	private final double[] secondLayerInputDerivatives;
	private double secondLayerBias = 0.0;

	private final double[][] firstLayerWeightGradients;
	private final double[] firstLayerBiasGradients;
	private final double[] secondLayerWeightGradients;
	private double secondLayerBiasGradient = 0.0;
	
	private final double[][] firstLayerEligibilityTraces;
	private final double[] firstLayerBiasEligibilityTraces;
	private final double[] secondLayerEligibilityTraces;
	private double secondLayerBiasEligibilityTrace = 0.0;
	
	private double[] firstLayerBeforeSigmoid;
	private double[] firstLayerAfterSigmoid;
	
	private double secondLayerBeforeSigmoid;
	private double secondLayerAfterSigmoid;
	
//	boolean useBiases = false;
	
	public TDGammonNeuralNetwork(double alpha, double lambda, double gamma, int inputUnits, int hiddenUnits)
	{		
		this.alpha = alpha;
		this.lambda = lambda;
		this.gamma = gamma;
		
		this.inputUnits = inputUnits;
		this.hiddenUnits = hiddenUnits;
		
      this.firstLayerActivationFunction = ActivationFunction.Sigmoid;
      this.secondLayerActivationFunction = ActivationFunction.Sigmoid;
		
		firstLayerWeights = new double[inputUnits][hiddenUnits];
		firstLayerBiases = new double[hiddenUnits];
		secondLayerWeights = new double[hiddenUnits];
		secondLayerBias = 0.0;
		
		firstLayerBeforeSigmoidDerivatives = new double[hiddenUnits];
		secondLayerInputDerivatives = new double[hiddenUnits];
		
		firstLayerWeightGradients = new double[inputUnits][hiddenUnits];
		firstLayerBiasGradients = new double[hiddenUnits];
		secondLayerWeightGradients = new double[hiddenUnits];
		secondLayerBiasGradient = 0.0;
		
		firstLayerEligibilityTraces = new double[inputUnits][hiddenUnits];
		firstLayerBiasEligibilityTraces = new double[hiddenUnits];
		secondLayerEligibilityTraces = new double[hiddenUnits];
		secondLayerBiasEligibilityTrace = 0.0;
		
		firstLayerBeforeSigmoid = new double[hiddenUnits];
		firstLayerAfterSigmoid = new double[hiddenUnits];
		
		for (int i=0; i<firstLayerWeights.length; i++)
		{
			for (int j=0; j<firstLayerWeights[i].length; j++)
			{
				firstLayerWeights[i][j] = -0.1 + 2.0 * 0.1 * Math.random();
			}
		}
		
		for (int i=0; i<secondLayerWeights.length; i++)
		{  
		   secondLayerWeights[i] = -0.1 + 2.0 * 0.1 * Math.random();
		}
		
		for (int i=0; i<firstLayerBiases.length; i++)
		{
			firstLayerBiases[i] = -0.1 + 2.0 * 0.1 * Math.random();
		}
		

		secondLayerBias = -0.1 + 2.0 * 0.1 * Math.random();
	}
	
	public void setAlpha(double alpha) 
	{
		this.alpha = alpha;
	}
	
	public void updateNeuralNetwork(double[] stateVector, double reward, double[] nextState)
	{
//		System.out.print("\nstateVector = ");
//		printArray(stateVector);
//		System.out.print("\nreward = " + reward);
//		System.out.print("\nnext stateVector = ");
//		printArray(nextState);
//		System.out.println();
		
		
		double nextValue = computeValue(nextState);
		double bootstrapValue = reward + gamma * nextValue;

//	    System.out.println("\nbootstrapValue = " + bootstrapValue);

		updateNeuralNetwork(stateVector, bootstrapValue);
	}
	
	private void printArray(double[] array)
	{
		for (int i=0; i<array.length; i++)
		{
			System.out.print(array[i]);
			if (i <array.length - 1) System.out.print(", ");
		}
	}

	public void updateNeuralNetwork(double[] stateVector, double bootstrapValue)
	{
		double thisValue = computeValue(stateVector);
		backpropogateDerivatives(stateVector);
		updateEligibilityTraces();

		double error = bootstrapValue - thisValue;
		double updateAmount = alpha * error;

		for (int i=0; i<firstLayerWeights.length; i++)
		{
			for (int j=0; j<firstLayerWeights[i].length; j++)
			{
				firstLayerWeights[i][j] += updateAmount * firstLayerEligibilityTraces[i][j];
			}
		}
		
		for (int i=0; i<firstLayerBiases.length; i++)
		{
			firstLayerBiases[i] += updateAmount * firstLayerBiasEligibilityTraces[i];
		}

		for (int i=0; i<secondLayerWeights.length; i++)
		{   
		   secondLayerWeights[i] += updateAmount * secondLayerEligibilityTraces[i];
		}
		
		for (int i=0; i<firstLayerBiases.length; i++)
      {
         firstLayerBiases[i] += updateAmount * firstLayerBiasEligibilityTraces[i];
      }
		secondLayerBias += updateAmount * secondLayerBiasEligibilityTrace;
	}

	public ActivationFunction getFirstLayerActivationFunction()
	{
	   return firstLayerActivationFunction;
	}

	public void setFirstLayerActivationFunction(ActivationFunction firstLayerActivationFunction)
	{
	   this.firstLayerActivationFunction = firstLayerActivationFunction;
	}

	public ActivationFunction getSecondLayerActivationFunction()
	{
	   return secondLayerActivationFunction;
	}

	public void setSecondLayerActivationFunction(ActivationFunction secondLayerActivationFunction)
	{
	   this.secondLayerActivationFunction = secondLayerActivationFunction;
	}

	public void setFirstLayerWeights(double[][] firstLayerWeights)
	{
		for (int i=0; i<this.firstLayerWeights.length; i++)
		{
			for (int j=0; j<this.firstLayerWeights[0].length; j++)
			{
				this.firstLayerWeights[i][j] = firstLayerWeights[i][j];
			}
		}
	}
	
	public void getFirstLayerWeights(double[][] firstLayerWeightsToPack)
	{
		for (int i=0; i<this.firstLayerWeights.length; i++)
		{
			for (int j=0; j<this.firstLayerWeights[0].length; j++)
			{
				firstLayerWeightsToPack[i][j] = this.firstLayerWeights[i][j];
			}
		}
	}
	
	public void getFirstLayerWeightGradients(double[][] firstLayerWeightGradientsToPack)
	{
		for (int i=0; i<this.firstLayerWeightGradients.length; i++)
		{
			for (int j=0; j<this.firstLayerWeightGradients[0].length; j++)
			{
				firstLayerWeightGradientsToPack[i][j] = this.firstLayerWeightGradients[i][j];
			}
		}
	}
	
	public void setFirstLayerBiases(double[] firstLayerBiases)
	{
		for (int i=0; i<this.firstLayerBiases.length; i++)
		{
			this.firstLayerBiases[i] = firstLayerBiases[i];
		}
	}

	public void getFirstLayerBiases(double[] firstLayerBiasesToPack)
	{
		for (int i=0; i<this.firstLayerBiases.length; i++)
		{
			firstLayerBiasesToPack[i] = this.firstLayerBiases[i];
		}
	}
	
	public void getFirstLayerBiasGradients(double[] firstLayerBiasGradientsToPack)
	{
		for (int i=0; i<this.firstLayerBiasGradients.length; i++)
		{
			firstLayerBiasGradientsToPack[i] = this.firstLayerBiasGradients[i];
		}
	}
	
	public void setSecondLayerWeights(double[] secondLayerWeights)
	{
		for (int i=0; i<this.secondLayerWeights.length; i++)
		{
			this.secondLayerWeights[i] = secondLayerWeights[i];
		}
	}

	public void getSecondLayerWeights(double[] secondLayerWeightsToPack)
	{
		for (int i=0; i<this.secondLayerWeights.length; i++)
		{
			secondLayerWeightsToPack[i] = this.secondLayerWeights[i];
		}
	}
	
	public void getSecondLayerWeightGradients(double[] secondLayerWeightGradientsToPack)
	{
		for (int i=0; i<this.secondLayerWeightGradients.length; i++)
		{
			secondLayerWeightGradientsToPack[i] = this.secondLayerWeightGradients[i];
		}
	}
	
	public void setSecondLayerBias(double secondLayerBias)
	{
		this.secondLayerBias = secondLayerBias;
	}

	public double getSecondLayerBias()
	{
		return secondLayerBias;
	}
	
	public double getSecondLayerBiasGradient()
	{
		return secondLayerBiasGradient;
	}
	
	private void updateEligibilityTraces()
	{		
		for (int i=0; i<firstLayerEligibilityTraces.length; i++)
		{
			for (int j=0; j<firstLayerEligibilityTraces[i].length; j++)
			{
				firstLayerEligibilityTraces[i][j] = gamma * lambda * firstLayerEligibilityTraces[i][j] + firstLayerWeightGradients[i][j];
			}
		}
		
		for (int i=0; i<firstLayerBiasEligibilityTraces.length; i++)
		{
			firstLayerBiasEligibilityTraces[i] = gamma * lambda * firstLayerBiasEligibilityTraces[i] + firstLayerBiasGradients[i];
		}
		
		for (int i=0; i<secondLayerEligibilityTraces.length; i++)
		{
			secondLayerEligibilityTraces[i] = gamma * lambda * secondLayerEligibilityTraces[i] + secondLayerWeightGradients[i];
		}
		
		secondLayerBiasEligibilityTrace = gamma * lambda * secondLayerBiasEligibilityTrace + secondLayerBiasGradient;
	}

	public double computeValue(double[] inputVector) 
	{
		for (int i=0; i<hiddenUnits; i++)
		{
			firstLayerBeforeSigmoid[i] = 0.0;
			for (int j=0; j<inputUnits; j++)
			{
				firstLayerBeforeSigmoid[i] += firstLayerWeights[j][i] * inputVector[j];
			}
			
			firstLayerBeforeSigmoid[i] += firstLayerBiases[i];

			firstLayerAfterSigmoid[i] = firstLayerActivationFunction(firstLayerBeforeSigmoid[i]);
		}

		secondLayerBeforeSigmoid = 0.0;
		for (int i=0; i<hiddenUnits; i++)
		{
			secondLayerBeforeSigmoid += secondLayerWeights[i] * firstLayerAfterSigmoid[i];
		}
		
		secondLayerBeforeSigmoid += secondLayerBias;

		secondLayerAfterSigmoid = secondLayerActivationFunction(secondLayerBeforeSigmoid);
		return secondLayerAfterSigmoid;
	}

	public void backpropogateDerivatives(double[] inputVector) 
	{
		double output = computeValue(inputVector);
		double outputErrorDerivative = 1.0;
		
		double errorDerivativeBeforeOutputSigmoid = outputErrorDerivative * getSecondLayerInputToOutputDerivative();
				
		for (int i=0; i<secondLayerWeightGradients.length; i++)
		{
			secondLayerWeightGradients[i] = errorDerivativeBeforeOutputSigmoid * firstLayerAfterSigmoid[i];
			secondLayerInputDerivatives[i] = errorDerivativeBeforeOutputSigmoid * secondLayerWeights[i];
			
			firstLayerBeforeSigmoidDerivatives[i] = secondLayerInputDerivatives[i] * getFirstLayerInputToOutputDerivative(i);
		}
		secondLayerBiasGradient = errorDerivativeBeforeOutputSigmoid;

		
		for (int i=0; i<hiddenUnits; i++)
		{
			for (int j=0; j<inputUnits; j++)
			{
				firstLayerWeightGradients[j][i] = firstLayerBeforeSigmoidDerivatives[i] * inputVector[j];
			}
			
			firstLayerBiasGradients[i] = firstLayerBeforeSigmoidDerivatives[i];
		}
	}

	private double firstLayerActivationFunction(double valueBeforeActivation) 
   {
	   return activationFunction(firstLayerActivationFunction, valueBeforeActivation);
   }
	
	private double secondLayerActivationFunction(double valueBeforeActivation) 
   {
      return activationFunction(secondLayerActivationFunction, valueBeforeActivation);
   }
	
	private double activationFunction(ActivationFunction activationFunction, double valueBeforeActivation) 
	{
	   switch (activationFunction)
	   {
	   case Linear:
	   {
	      return valueBeforeActivation;
	   }
	   case RectifiedLinear:
	   {
	      if (valueBeforeActivation <= 0.0) return 0.0;
	      return valueBeforeActivation;
	   }
	   case Sigmoid:
	   {
	      return logisticFunction(valueBeforeActivation);
	   }
	   default:
	   {
	      throw new RuntimeException("Shouldn't get here!");
	   }
	   }
	}
	
	private double getInputToOutputDerivative(ActivationFunction activationFunction, double valueBeforeActivation, double valueAfterActivation) 
   {
      switch (activationFunction)
      {
      case Linear:
      {
         return 1.0;
      }
      case RectifiedLinear:
      {
         if (valueBeforeActivation <= 0.0) return 0.0;
         return 1.0;
      }
      case Sigmoid:
      {
         return valueAfterActivation * (1.0 - valueAfterActivation); // Derivative for sigmoid y=f(x) is dy/dx = y(1-y)
      }
      default:
      {
         throw new RuntimeException("Shouldn't get here!");
      }
      }
   }
	
	private double getSecondLayerInputToOutputDerivative() 
	{
	   return getInputToOutputDerivative(secondLayerActivationFunction, secondLayerBeforeSigmoid, secondLayerAfterSigmoid);
	}

	private double getFirstLayerInputToOutputDerivative(int i) 
	{
	     return getInputToOutputDerivative(firstLayerActivationFunction, firstLayerBeforeSigmoid[i], firstLayerAfterSigmoid[i]);
	}

	public static double logisticFunction(double input) 
	{
		return 1.0 / (1.0 + Math.exp(-input));
	}

	public void printWeights() 
	{
		System.out.println("\ndouble[][] firstLayerWeights = new double[][]{");

		for (int i=0; i<firstLayerWeights.length; i++)
		{
			System.out.print("{");
			for (int j=0; j<firstLayerWeights[i].length; j++)
			{
				System.out.print(firstLayerWeights[i][j]);
				if (j < firstLayerWeights[i].length - 1) System.out.print(", ");
			}
			System.out.print("}");
			if (i < firstLayerWeights.length - 1) System.out.println(", ");
		}
		
		System.out.println("\n};");

		printSingleDimensionArray("firstLayerBiases", firstLayerBiases);
//		System.out.println();
		
		printSingleDimensionArray("secondLayerWeights", secondLayerWeights);
		System.out.println();
		
		System.out.println("secondLayerBias = " + secondLayerBias + ";");
		System.out.println();

	}

	private static void printSingleDimensionArray(String name, double[] values) 
	{
		System.out.print("\ndouble[] " + name + " = new double[]{");

		for (int i=0; i<values.length; i++)
		{
			System.out.print(values[i]);
			if (i < values.length - 1) System.out.print(", ");
		}

		System.out.println("};");
		
	}

	public void resetEligibilityTraces() 
	{
		for (int i=0; i<firstLayerEligibilityTraces.length; i++)
		{
			for (int j=0; j<firstLayerEligibilityTraces[i].length; j++)
			{
				firstLayerEligibilityTraces[i][j] = 0.0;
			}
		}
		
		for (int i=0; i<firstLayerBiasEligibilityTraces.length; i++)
		{
			firstLayerBiasEligibilityTraces[i] = 0.0;
		}
		
		for (int i=0; i<secondLayerEligibilityTraces.length; i++)
		{
			secondLayerEligibilityTraces[i] = 0.0;
		}
		
		secondLayerBiasEligibilityTrace = 0.0;
	}
	
	
}




