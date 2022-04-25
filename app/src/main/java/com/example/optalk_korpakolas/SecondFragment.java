package com.example.optalk_korpakolas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.optalk_korpakolas.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private int N = 10;
    private double stepSize = 0.0001;
    private CanvasView canvasView;
    private CirclePacking circlePacking;
    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        N = getArguments().getInt("numOfCircles",10);
        stepSize = getArguments().getDouble("stepSize",0.0001);
        canvasView = new CanvasView(getActivity());
        circlePacking = new CirclePacking(canvasView);
        circlePacking.init(N);
        circlePacking.updateMinDistance();
        canvasView.invalidate();

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        binding.canvas.addView(canvasView);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_FirstFragment);
        });

        binding.startCirclePacking.setOnClickListener(view1 -> {
            circlePacking.iterate(stepSize);
            updateTable();
        });

        binding.iterateOnce.setOnClickListener(view1 -> {
            circlePacking.iterateOnce();
            updateTable();
        });

    }

    private void updateTable() {
        Double distance = circlePacking.getMinDistance();
        double radius = (distance / 2);
        double circaArea =  radius * radius * Math.PI * N;
        double extendedArea = (1+radius*2) * (1+radius*2);
        Double area = circaArea / extendedArea;
        Double stepSize = circlePacking.getStepSize();
        Integer numOfCircles = N;
        binding.viewArea.setText(area.toString());
        binding.viewNumOfCircles.setText(numOfCircles.toString());
        binding.viewStepSize.setText(stepSize.toString());
        binding.viewDistance.setText(distance.toString());
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public class CanvasView extends View {

        public CanvasView(Context context) {
            super (context);
            this.paint = new Paint();
            this.sharedRadius = 100;
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            height = getHeight();
            width = getWidth();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            //circleManager.genTestCircles();
            sharedRadius = (float)circlePacking.getMinDistance() / 2;

            float drawScale = getWidth() / (1 + sharedRadius * 2);
            float drawOffset = sharedRadius * drawScale;

            paint.setColor(Color.parseColor("#999999"));
            canvas.drawRect(
                    0 + drawOffset,
                    0 + drawOffset,
                    1 * drawScale + drawOffset,
                    1 * drawScale + drawOffset,
                    paint
            );

            paint.setColor(Color.parseColor("#773377"));
            for (int i = 0; i < N; i++) {
                Circle c = circlePacking.getCircle(i);
                canvas.drawCircle(
                        drawScale * (float)c.getX() + drawOffset,
                        drawScale * (float)c.getY() + drawOffset,
                        sharedRadius * drawScale,
                        paint
                );
            }
        }

        protected Paint paint;
        protected float sharedRadius;
        protected float height;
        protected float width;
    }

}